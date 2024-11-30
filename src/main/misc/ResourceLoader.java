package main.misc;

import main.Main;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.function.BiConsumer;

public class ResourceLoader {

    private final PApplet p;

    public ResourceLoader(PApplet p) {
        this.p = p;
    }

    public void loadSprites() {
        new Walker(
                "sprites",
                ".png",
                /* Trying to wrangle my horrible and inconsistent naming conventions */
                (path, names) -> {
                    PImage image = p.loadImage(path);
                    StringBuilder key = new StringBuilder();
                    String suffix;
                    switch (names[0]) {
                        case "enemies" -> {
                            key = new StringBuilder(names[1] + Utilities.capitalize(names[2]));
                            suffix = "EN";
                        } case "gui" -> {
                            if (Objects.equals(names[1], "buttons")) {
                                key = new StringBuilder(names[2]);
                                suffix = "BT";
                            } else if (Objects.equals(names[1], "panels")) {
                                key = new StringBuilder(names[2]);
                                suffix = "PN";
                            } else {
                                if (names[names.length-1].matches("[0-9][0-9][0-9]")) {
                                    key = new StringBuilder(names[names.length - 2]);
                                } else {
                                    key = new StringBuilder(names[names.length - 1]);
                                }
                                suffix = "IC";
                            }
                        } case "machines" -> {
                            if (Objects.equals(names[2], "base")) {
                                key = new StringBuilder(names[1]);
                            } else {
                                key = new StringBuilder(names[1] + names[2]);
                            }
                            suffix = "MA";
                        } case "particles" -> {
                            if (names.length == 4) {
                                key = new StringBuilder(names[2] + Utilities.capitalize(names[1]));
                            } else {
                                if (Objects.equals(names[1], "debris")) {
                                    key = new StringBuilder(names[2]);
                                } else {
                                    key = new StringBuilder(names[1]);
                                }
                            }
                            suffix = "PT";
                        } case "projectiles" -> {
                            key = new StringBuilder(names[1]);
                            suffix = "PJ";
                        } case "tiles" -> {
                            switch (names[1]) {
                                case "base" -> {
                                    key = new StringBuilder(names[2] + "Ba_");
                                    if (names.length == 4 && !Objects.equals(names[names.length - 1], "base")) {
                                        key.append(names[names.length - 1].toUpperCase()).append("_");
                                    }
                                } case "breakables" -> {
                                    if (names.length == 4) {
                                        if (names[names.length-1].matches("[0-9]")) {
                                            key = new StringBuilder(names[2] + names[3]);
                                        } else {
                                            key = new StringBuilder(names[3] + Utilities.capitalize(names[2]));
                                        }
                                    } else {
                                        key = new StringBuilder(names[2]);
                                    }
                                    key.append("Br_");
                                } case "decoration" -> {
                                    key = new StringBuilder(names[2]);
                                    if (names.length == 4) {
                                        String k = names[3];
                                        if (k.matches("[tb][lr]d?")) {
                                            k = k.toUpperCase();
                                        } else {
                                            k = Utilities.capitalize(k);
                                        }
                                        key.append(k);
                                    }
                                    key.append("De_");
                                } case "flooring" -> {
                                    key = new StringBuilder(names[2] + "Fl_");
                                    if (!Objects.equals(names[3], "base")) {
                                        key.append(names[3].toUpperCase()).append("_");
                                    }
                                }
                                case "obstacles" -> {
                                    key = new StringBuilder(names[2]);
                                    for (int i = 3; i < names.length; i++) {
                                        String k = names[i];
                                        /* (left/right or (top/bottom and maybe left/right))
                                           and maybe diagonal/corner and maybe color */
                                        if (k.matches("([lr]|([tb][lr]?))[dc]?[pgb]?")) {
                                            k = k.toUpperCase();
                                        } else {
                                            // also matches "T2", but thats fine
                                            k = Utilities.capitalize(k);
                                        }
                                        key.append(k);
                                    }
                                    key.append("Ob_");
                                }
                            }
                            suffix = "TL";
                            System.out.println(path + " -> " + key + suffix);
                        }
                        default -> {
//                            System.out.println("Invalid parent directory");
                            return;
                        }
                    }
                    // is animation
                    if (names[names.length-1].matches("[0-9][0-9][0-9]")) {
                        suffix = suffix.toUpperCase();
                        int idx = Integer.parseInt(names[names.length-1]);
                        PImage[] o = Main.animatedSprites.get(key + suffix);
                        if (o == null) o = new PImage[0];
                        PImage[] n = new PImage[PApplet.max(o.length, idx+1)];
                        System.arraycopy(o, 0, n, 0, o.length);
                        n[idx] = image;
                        Main.animatedSprites.put(key + suffix, n);
                    } else {
                        suffix = Utilities.capitalize(suffix.toLowerCase());
                        Main.staticSprites.put(key + suffix, image);
                    }
                }
        ).walk();
    }

    /**
     * Thanks, Raine!
     * Modified for this use case.
     */
    private static class Walker extends SimpleFileVisitor<Path> {
        private static final Path ROOT_PATH = Paths.get("resources").toAbsolutePath();

        private final String folder;
        private final String extension;
        private final BiConsumer<String, String[]> itemLoader;

        public Walker(String folder, String extension, BiConsumer<String, String[]> itemLoader) {
            this.folder = folder;
            this.extension = extension;
            this.itemLoader = itemLoader;
        }

        public void walk() {
            try {
                Files.walkFileTree(
                        ROOT_PATH.resolve(folder),
                        this
                );
            } catch (IOException e) {
                throw new RuntimeException(e.toString());
            }
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (!file.toString().endsWith(extension)) return FileVisitResult.CONTINUE;

            String path = ROOT_PATH.relativize(file).toString();
            String[] names = path
                    .substring(folder.length() + 1, path.length() - 4)
                    .split("\\/");

            itemLoader.accept(file.toString(), names);

            return FileVisitResult.CONTINUE;
        }
    }
}

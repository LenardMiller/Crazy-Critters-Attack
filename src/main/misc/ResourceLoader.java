package main.misc;

import main.Main;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

public class ResourceLoader {

    private final PApplet p;

    public ResourceLoader(PApplet p) {
        this.p = p;
    }

    public static <T> T getResource(String key, HashMap<String, T> map) {
        T r = map.get(key);
        if (r == null) {
            System.err.println("Failed to get resource '" + key + "'");
        }
        return r;
    }

    public void loadSprites() {
        new Walker(
                "sprites",
                ".png",
                /* Trying to wrangle my horrible and inconsistent naming conventions */
                (path, files) -> {
                    PImage image = p.loadImage(path);
                    StringBuilder key = new StringBuilder();
                    String suffix = "";
                    switch (files[0]) {
                        case "enemies" -> {
                            key = new StringBuilder(files[1] + Utilities.capitalize(files[2]));
                            suffix = "EN";
                        } case "gui" -> {
                            if (Objects.equals(files[1], "buttons")) {
                                key = new StringBuilder(files[2]);
                                suffix = "BT";
                            } else if (Objects.equals(files[1], "panels")) {
                                key = new StringBuilder(files[2]);
                                suffix = "PN";
                            } else {
                                if (files[files.length-1].matches("[0-9][0-9][0-9]")) {
                                    key = new StringBuilder(files[files.length - 2]);
                                } else {
                                    key = new StringBuilder(files[files.length - 1]);
                                }
                                suffix = "IC";
                            }
                        } case "machines" -> {
                            if (Objects.equals(files[2], "base")) {
                                key = new StringBuilder(files[1]);
                            } else {
                                key = new StringBuilder(files[1] + files[2]);
                            }
                            suffix = "MA";
                        } case "particles" -> {
                            if (files.length == 4) {
                                key = new StringBuilder(files[2] + Utilities.capitalize(files[1]));
                            } else {
                                if (Objects.equals(files[1], "debris")) {
                                    key = new StringBuilder(files[2]);
                                } else {
                                    key = new StringBuilder(files[1]);
                                }
                            }
                            suffix = "PT";
                        } case "projectiles" -> {
                            key = new StringBuilder(files[1]);
                            suffix = "PJ";
                        } case "tiles" -> {
                            switch (files[1]) {
                                case "base" -> {
                                    key = new StringBuilder(files[2] + "Ba_");
                                    if (files.length == 4 && !Objects.equals(files[files.length - 1], "base")) {
                                        key.append(files[files.length - 1].toUpperCase()).append("_");
                                    }
                                } case "breakables" -> {
                                    if (files.length == 4) {
                                        if (files[files.length-1].matches("[0-9]")) {
                                            key = new StringBuilder(files[2] + files[3]);
                                        } else {
                                            key = new StringBuilder(files[3] + Utilities.capitalize(files[2]));
                                        }
                                    } else {
                                        key = new StringBuilder(files[2]);
                                    }
                                    key.append("Br_");
                                } case "decoration" -> {
                                    key = new StringBuilder(files[2]);
                                    if (files.length == 4) {
                                        String k = files[3];
                                        if (k.matches("[tb][lr]d?")) {
                                            k = k.toUpperCase();
                                        } else {
                                            k = Utilities.capitalize(k);
                                        }
                                        key.append(k);
                                    }
                                    key.append("De_");
                                } case "flooring" -> {
                                    key = new StringBuilder(files[2] + "Fl_");
                                    if (!Objects.equals(files[3], "base")) {
                                        key.append(files[3].toUpperCase()).append("_");
                                    }
                                }
                                case "obstacles" -> {
                                    key = new StringBuilder(files[2]);
                                    for (int i = 3; i < files.length; i++) {
                                        String k = files[i];
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
                        } case "towers" -> {
                            if (Objects.equals(files[1], "turrets")) {
                                for (int i = 2; i < files.length; i++) {
                                    String k = files[i];
                                    if (i > 2) k = Utilities.capitalize(k);
                                    if (!k.matches("[0-9][0-9][0-9]")) {
                                        key.append(k);
                                    }
                                }
                                suffix = "TR";
                            } else if (Objects.equals(files[1], "walls")) {
                                if (files.length == 3) {
                                    key.append(files[2]);
                                } else {
                                    // is center
                                    if (files[3].matches("[0-9][0-9][0-9]")) {
                                        key.append(files[2]).append("Wall");
                                    } else {
                                        key.append(files[2]).append(files[3]).append("Wall");
                                    }
                                }
                                suffix = "TW";
                                System.out.println(path + " -> " + key + suffix);
                            }
                        }
                        default -> {
//                            System.err.println("Invalid parent directory");
                            return;
                        }
                    }
                    // is animation
                    if (files[files.length-1].matches("[0-9][0-9][0-9]")) {
                        suffix = suffix.toUpperCase();
                        int idx = Integer.parseInt(files[files.length-1]);
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

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
                (path, names) -> {
                    PImage image = p.loadImage(path);
                    String key = "";
                    String suffix = "";
                    switch (names[0]) {
                        case "enemies" -> {
                            key = names[1] + Utilities.capitalize(names[2]);
                            suffix = "EN";
                        } case "gui" -> {
                            if (Objects.equals(names[1], "buttons")) {
                                key = names[2];
                                suffix = "BT";
                            } else if (Objects.equals(names[1], "panels")) {
                                key = names[2];
                                suffix = "PN";
                            } else {
                                if (names[names.length-1].matches("[0-9][0-9][0-9]")) {
                                    key = names[names.length-2];
                                } else {
                                    key = names[names.length-1];
                                }
                                suffix = "IC";
                            }
                        } case "machines" -> {
                            if (Objects.equals(names[2], "base")) {
                                key = names[1];
                            } else {
                                key = names[1] + names[2];
                            }
                            suffix = "MA";
                        } case "particles" -> {
                            if (names.length == 4) {
                                key = names[2] + Utilities.capitalize(names[1]);
                            } else {
                                if (Objects.equals(names[1], "debris")) {
                                    key = names[2];
                                } else {
                                    key = names[1];
                                }
                            }
                            suffix = "PT";
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

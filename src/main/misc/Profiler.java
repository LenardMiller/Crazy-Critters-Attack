package main.misc;

import processing.core.PApplet;

import java.util.Arrays;
import java.util.HashMap;

public class Profiler {

    private static class Profile {

        private final int maxCount;
        private final int[] elapsed;

        private int start;
        private int count;

        private Profile(int start, int maxCount) {
            this.maxCount = maxCount;
            this.start = start;
            elapsed = new int[maxCount];
        }

        private void start(int time) {
            start = time;
        }

        private boolean finish(int time, String name) {
            elapsed[count] = time - start;
            count++;
            if (count == maxCount) {
                if (count == 1) {
                    System.out.println("[PROFILING] " + name + ": " + elapsed[0] + " ms");
                } else {
                    System.out.println("[PROFILING] " + name + ": " +
                            (int) Arrays.stream(elapsed).average().orElseThrow() + " ms (" +
                            Arrays.stream(elapsed).max().orElseThrow() + " ms)");
                }
                return true;
            }
            return false;
        }
    }

    public static final int PROFILE_TIME = 30 * 60;

    private final PApplet p;
    private final HashMap<String, Profile> profiles;
    private final boolean enabled;

    public Profiler(PApplet p, boolean enabled) {
        this.p = p;
        profiles = new HashMap<>();
        this.enabled = enabled;
    }

    public void startProfilingSingle(String name) {
        if (!enabled) return;
        profiles.put(name, new Profile(p.millis(), 1));
    }

    public void startProfiling(String name, int count) {
//        System.out.println("start " + name + ": " + p.millis());
        if (!enabled) return;
        if (profiles.containsKey(name)) {
            profiles.get(name).start(p.millis());
        } else {
            profiles.put(name, new Profile(p.millis(), count));
        }
    }

    public void finishProfiling(String name) {
//        System.out.println("finish " + name + ": " + p.millis());
        if (!enabled) return;
        if (!profiles.containsKey(name)) {
            System.err.println("[PROFILING] no key " + name);
            return;
        }
        if (profiles.get(name).finish(p.millis(), name)) {
            profiles.remove(name);
        }
    }
}

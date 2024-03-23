package main.misc;

import processing.data.JSONObject;

public class Settings {

    public boolean restartRequired;

    private float volume;
    private int ringResolution;
    private boolean isFullscreen, hasGore, useOpenGL;

    public Settings(float volume, int ringResolution, boolean isFullscreen, boolean hasGore, boolean useOpenGL) {
        this.volume = volume;
        this.isFullscreen = isFullscreen;
        this.hasGore = hasGore;
        this.useOpenGL = useOpenGL;
        this.ringResolution = ringResolution;
    }

    public Settings(JSONObject object) {
        this(
            object.getFloat("volume", 0.25f),
            object.getInt("ringResolution", 50),
            object.getBoolean("fullscreen", true),
            object.getBoolean("gore", true),
            object.getBoolean("useOpenGL", false)
        );
    }

    //this is not a copy constructor
    @SuppressWarnings("CopyConstructorMissesField")
    public Settings(Settings old) {
        this();
        this.restartRequired = (isFullscreen != old.isFullscreen || useOpenGL != old.useOpenGL);
    }

    public Settings() {
        this(0.25f, 30, true, true, false);
    }

    public void save(JSONObject object) {
        object.setFloat("volume", volume);
        object.setInt("ringResolution", ringResolution);
        object.setBoolean("fullscreen", isFullscreen);
        object.setBoolean("gore", hasGore);
        object.setBoolean("useOpenGL", useOpenGL);
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        if (fullscreen != isFullscreen) restartRequired = true;
        isFullscreen = fullscreen;
    }

    public boolean isHasGore() {
        return hasGore;
    }

    public void setHasGore(boolean hasGore) {
        this.hasGore = hasGore;
    }

    public boolean isUseOpenGL() {
        return useOpenGL;
    }

    public void setUseOpenGL(boolean useOpenGL) {
        if (useOpenGL != this.useOpenGL) restartRequired = true;
        this.useOpenGL = useOpenGL;
    }

    public int getRingResolution() {
        return ringResolution;
    }

    public void setRingResolution(int ringResolution) {
        this.ringResolution = ringResolution;
    }
}

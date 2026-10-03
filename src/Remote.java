public abstract class Remote {
    private final String id;
    private final int volumePreset;
    private Device implementation;

    protected Remote(String id, int volumePreset, Device implementation) {
        this.id = id;
        this.volumePreset = volumePreset;
        this.implementation = implementation;
    }

    public String getId() {
        return id;
    }

    public int getVolumePreset() {
        return volumePreset;
    }

    public void setImplementation(Device implementation) {
        this.implementation = implementation;
    }

    public String execute() {
        return implementation.applySettings(true, volumePreset);
    }
}

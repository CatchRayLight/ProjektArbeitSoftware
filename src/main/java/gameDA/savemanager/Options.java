package gameDA.savemanager;

public class Options {

    private boolean music;
    private boolean autoSave;

    public Options(boolean music, boolean autoSave) {
        this.music = music;
        this.autoSave = autoSave;
    }

    //Getter and Setter


    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }
}

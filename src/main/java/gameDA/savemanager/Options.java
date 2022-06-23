package gameDA.savemanager;

public class Options {

    private boolean music;//Speichert ob Musik an oder aus ist
    private boolean autoSave;//Speichert ob Autosave an oder aus ist

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

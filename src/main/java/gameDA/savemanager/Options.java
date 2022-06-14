package gameDA.savemanager;

public class Options {

    private boolean music;

    public Options(boolean music) {
        this.music = music;
    }

    //Getter and Setter

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }
}

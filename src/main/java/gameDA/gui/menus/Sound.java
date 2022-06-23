package gameDA.gui.menus;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    private Clip clip; //Der Sound der gerade ausgewählt ist
    URL[] soundUrl = new URL[2]; //Pfade der verschiedenen Sound Dateien

    public Sound() {
        //Lade die Sound Dateien
        soundUrl[0] = getClass().getResource("/sounds/Music.wav");
        soundUrl[1] = getClass().getResource("/sounds/DeathScreenMusic.wav");
    }

    /**
     * Lade einen Clip
     * @param i Index der Sound Datei die benutzt werden soll
     */
    public void setClip(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Spiele den Clip ab
     */
    public void play() {
        clip.start();
    }

    /**
     * Spiele den Clip kontinuirlich ab
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stoppe den Clip
     */
    public void stop() {
        if(clip != null) clip.stop();
    }

}

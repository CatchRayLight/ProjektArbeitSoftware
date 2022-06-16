package gameDA.gui.menus;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    URL soundUrl[] = new URL[30];

    public Sound() {
        soundUrl[0] = getClass().getResource("/sounds/Music.wav");
        soundUrl[1] = getClass().getResource("/sounds/DeathScreenMusic.wav");
    }

    public void setClip(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        if(clip != null) clip.stop();
    }

}

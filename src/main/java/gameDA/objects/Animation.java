package gameDA.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {

    private int speed; //Geschwindigkeit der Animation in frames pro Bild
    private final int frames; //Anzahl an frames für die Animation
    private int index = 0; //Anzahl an vergangenen Frames
    private int count = 0;

    private final BufferedImage[] img = new BufferedImage[14]; //Bilder der Animation
    private BufferedImage currentImg;//momentanes Image

    public Animation(int speed, BufferedImage[] img){
        this.speed = speed;
        System.arraycopy(img, 0, this.img, 0, img.length);
        frames = img.length;
    }

    /**
     * Führt die Animation aus
     */
    public void runAnimation(){
        //wechselt den Frame zyklisch je nach speed der Animation
        index++;
        if(index > speed){
            index = 0;
            nextFrame();
        }
    }

    /**
     * Sets the current Frame to the next Frame based on speed
     */
    public void nextFrame(){
        //Basierend auf Speed wird der nächst frame zum momentanen Frame gestzt
        for(int i = 2; i < img.length; i++) {
            if(frames == i) {
                for(int j = 0; j < i; j++) {
                    if(count == j) currentImg = img[j];
                }
                count++;
                if(count > frames)
                    count = 0;
                break;
            }
        }
    }

    /**
     * Zeichnet das momentane Image der Animation
     * @param g Die Grafik mit der gerendert werdeb soll
     * @param x X Position
     * @param y Y Position
     * @param offset Das Offset
     */
    public void drawAnimation(Graphics g, double x, double y, int offset){
        g.drawImage(currentImg, (int)x - offset, (int)y, null);
    }
}

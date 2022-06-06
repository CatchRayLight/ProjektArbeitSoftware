package gameDA.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int frames;
    private int index = 0;
    private int count = 0;

    private BufferedImage[] img = new BufferedImage[14];
    private BufferedImage currentImg;

    public Animation(int speed, BufferedImage[] img){
        this.speed = speed;
        for(int i = 0; i < img.length; i++) {
            this.img[i] = img[i];
        }
        frames = img.length;
    }
    public void runAnimation(){
        index++;
        if(index > speed){
            index = 0;
            nextFrame();
        }
    }
    public void nextFrame(){
        for(int i = 2; i < img.length; i++) {
            if(frames == i) {
                for(int j = 0; j < i; j++) {
                    if(count == j)
                        currentImg = img[j];
                }
                count++;
                if(count > frames)
                    count = 0;
                break;
            }
        }
    }

    public void drawAnimation(Graphics g, double x, double y, int offset){
        g.drawImage(currentImg, (int)x - offset, (int)y, null);
    }

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return count;
    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
}

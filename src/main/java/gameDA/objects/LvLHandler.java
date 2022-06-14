package gameDA.objects;

import gameDA.config.output.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class LvLHandler {

    private final BufferedImage lvL3;
    private final BufferedImage lvL1;
    private final BufferedImage lvL2;


    public LvLHandler(){
        BufferedImageLoader loader = new BufferedImageLoader();
        lvL3 = loader.loadImage("/TestLVL.png");
        lvL2 = loader.loadImage("/Level2.png");
        lvL1 = loader.loadImage("/Level1.png");
    }

    public BufferedImage getLvL(int lvl) {
        if(lvl == 1) {
            return lvL1;
        }
        if (lvl == 2){
            return lvL2;
        }
        if (lvl == 3){
            return lvL3;
        }
        return null;
    }
}

package gameDA.objects;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.objects.model.Player;

import java.awt.image.BufferedImage;

public class LvLHandler {

    private final BufferedImage lvL3;
    private final BufferedImage lvL1;
    private final BufferedImage lvL2;



    public LvLHandler(){
        BufferedImageLoader loader = new BufferedImageLoader();
        lvL1 = loader.loadImage("/TestLVL.png");
        lvL2 = loader.loadImage("/Level1.png");
        lvL3 = loader.loadImage("/Level2.png");
    }

    public BufferedImage setLvL(int lvl) {
        if(lvl == 1) {
            Game.getGame().setLvLInt(1);
            return lvL1;
        }
        if (lvl == 2){
            Game.getGame().setLvLInt(2);
            return lvL2;
        }
        if (lvl == 3){
            Game.getGame().setLvLInt(3);
            return lvL3;
        }
        return null;
    }
    public void nextLvL(ObjectHandler objectHandler){
        Game.getGame().setLvLInt(
                Game.getGame().getLvLInt() + 1);
        for (int i = 0; i <objectHandler.gameObjects.size() ; i++) {
            GameObject tempObj = objectHandler.gameObjects.get(i);
            if(tempObj.getId() == ObjectID.PLAYER){
                objectHandler.gameObjects.clear();
                objectHandler.gameObjects.add(tempObj);
                tempObj.setX(100);
                tempObj.setY(100);
                objectHandler.setLeft(false);
                objectHandler.setRight(false);
                objectHandler.setUp(false);
                objectHandler.setDown(false);
                objectHandler.setSpace(false);
                Game.getGame().levelBuilder(setLvL(Game.getGame().getLvLInt()));
                break;
            }
        }
    }
}

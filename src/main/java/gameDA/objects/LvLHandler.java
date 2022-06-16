package gameDA.objects;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.objects.model.Player;

import java.awt.image.BufferedImage;

public class LvLHandler {

    private final BufferedImage lvL3;
    private final BufferedImage lvL1;
    private final BufferedImage lvL2;
    private final BufferedImage onPlanet;

    private final BufferedImage onPlanet2;

    public LvLHandler(){
        BufferedImageLoader loader = new BufferedImageLoader();
        lvL1 = loader.loadImage("/level/Level1.png");
        lvL2 = loader.loadImage("/level/Level3.png");
        lvL3 = loader.loadImage("/level/Level3.png");
        onPlanet = loader.loadImage("/level/OnPlanetPng.png");
        onPlanet2= loader.loadImage("/level/OnPlanetPng2.png");
    }

    public BufferedImage setLvL(int lvl) {
        if(lvl == 0) return onPlanet;
        if(lvl % 2 == 0)return onPlanet2;
        if(lvl == 1)return lvL1;
        if (lvl == 3)return lvL2;
        if (lvl == 5)return lvL3;
        return null;
    }
    public void nextLvL(ObjectHandler objectHandler){
        Game.getGame().setLvLInt(
                Game.getGame().getLvLInt() + 1);
        System.out.println(Game.getGame().getLvLInt());
        for (int i = 0; i <objectHandler.gameObjects.size() ; i++) {
            GameObject tempObj = objectHandler.gameObjects.get(i);
            if(tempObj.getId() == ObjectID.PLAYER){
                objectHandler.gameObjects.clear();
                objectHandler.gameObjects.add(tempObj);
                Player player = (Player) tempObj;
                player.toggleOnPlanet();
                Game.getGame().togglePlanet();
                player.setX(100);
                player.setY(100);
                if(player.isOnPlanet()){
                    player.setX(300);
                    player.setY(400);
                }
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

package gameDA.objects;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.objects.model.Player;

import java.awt.image.BufferedImage;

public class LvLHandler {

    private final BufferedImage lvL3;
    private final BufferedImage lvL1;
    private final BufferedImage victory;
    private final BufferedImage bossLvL;
    private final BufferedImage lvL2;
    private final BufferedImage onPlanet;

    private final BufferedImage onPlanet2;

    public LvLHandler(){
        BufferedImageLoader loader = new BufferedImageLoader();
        lvL1 = loader.loadImage("/level/Level1.png");
        lvL2 = loader.loadImage("/level/Level2.png");
        lvL3 = loader.loadImage("/level/Level3.png");
        onPlanet = loader.loadImage("/level/OnPlanetPng.png");
        onPlanet2= loader.loadImage("/level/OnPlanetPng2.png");
        victory = loader.loadImage("/level/victory.png");
        bossLvL = loader.loadImage("/level/bossLvL.png");

    }
    //get PNG of lvl by sorting by parameter lvl
    public BufferedImage getLvLImage(int lvl) {
        if(lvl == 0) return onPlanet;
        if(lvl % 3 == 0 && lvl != 9)return onPlanet2;
        if(lvl == 1)return lvL1;
        if(lvl == 2||lvl == 5||lvl == 8)return bossLvL;
        if (lvl == 4)return lvL2;
        if (lvl == 7)return lvL3;
        if(lvl >= 9)return victory;
        return null;
    }
    public void nextLvL(ObjectHandler objectHandler){
        //next lvl
        Game.getGame().setLvLInt(
                Game.getGame().getLvLInt() + 1);
        Player player = Game.getGame().getObjectHandler().getPlayer();
        //clearing current level from objects
        objectHandler.gameObjects.clear();
        //saving the player in new lvl
        objectHandler.gameObjects.add(player);
        //gets player
        //default setting player  in middle of screen
        player.setX(Game.SCREEN_WIDTH/2);
        player.setY(Game.SCREEN_HEIGHT/2);
        //if is default equals bosslvl
        Game.getGame().setBossLvl(true);
        //if space
        if(!(Game.getGame().getLvLInt() == 2 || Game.getGame().getLvLInt() == 5 || Game.getGame().getLvLInt() == 8)){
            player.toggleOnPlanet();
            player.setX(100);
            player.setY(100);
            Game.getGame().togglePlanet();
            Game.getGame().setBossLvl(false);
        }

        if(player.isOnPlanet()){
            Game.getGame().setBossLvl(false);
            player.setX(300);
            player.setY(400);
        }
        //always set directions false (if not its moving)
        objectHandler.setLeft(false);
        objectHandler.setRight(false);
        objectHandler.setUp(false);
        objectHandler.setDown(false);
        objectHandler.setSpace(false);
        //build new lvl after incrementing lvl by 1
        Game.getGame().levelBuilder(getLvLImage(Game.getGame().getLvLInt()));
        //Autosave
        if((Game.getGame().getLvLInt() == 0 || Game.getGame().getLvLInt() % 3 == 0) && Game.getGame().getOptions().isAutoSave()) {
            Game.getGame().getSafeManager().safe(0);
        }
    }
}

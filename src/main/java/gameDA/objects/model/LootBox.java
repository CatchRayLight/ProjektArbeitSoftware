package gameDA.objects.model;

import gameDA.config.output.SpriteSheet;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

import static gameDA.objects.model.Player.playerHealthbar;


public class LootBox extends GameObject {
    private final ObjectHandler objectHandler;

    private final BufferedImage img;

    public LootBox(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;
        img = spriteSheet.getImage(7,6,32,32);
    }
    @Override
    public void update() {
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                if(getBounds().intersects(tempObject.getBounds())){
                    objectHandler.removeObj(this);
                    Player player = (Player) tempObject;
                    //add stuff /coins what ever
                    player.setAmmo(Math.min(player.getAmmo()+100, 100));
                    player.setHp(Math.min(player.getHp()+50,100));
                    player.setPlayerCoins(player.getPlayerCoins()+(int)(Math.random() * 15));
                    playerHealthbar.setHp(player.getHp());
                    playerHealthbar.setAmmo(player.getAmmo());
                    playerHealthbar.setPlayerCoins(player.getPlayerCoins());
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img,x,y,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    @Override
    public Rectangle getTopBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getRightBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getLeftBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getBotBounds(int offset) {
        return null;
    }
}

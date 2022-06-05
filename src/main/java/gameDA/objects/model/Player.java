package gameDA.objects.model;


import gameDA.config.output.SpriteSheet;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;
import java.awt.*;

import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler objectHandler;
    private BufferedImage playerSpriteR;
    private BufferedImage playerSpriteL;
    private BufferedImage playerSpriteU;
    private BufferedImage playerSpriteD;
    private int speed = 5;


    public Player(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler) {
        super(x, y, id,spriteSheet);
        this.objectHandler = objectHandler;
        //78
        playerSpriteR = spriteSheet.getImage(7,8,32,32);
        playerSpriteL = spriteSheet.getImage(8,8,32,32);
        playerSpriteU = spriteSheet.getImage(7,7,32,32);
        playerSpriteD = spriteSheet.getImage(8,7,32,32);
    }

    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        collisionWithWall();
        playerMovement();
    }


    @Override
    public void render(Graphics g) {
        if(objectHandler.isLeft()){
            g.drawImage(playerSpriteL,x,y,null);
        }else if(objectHandler.isRight()){
            g.drawImage(playerSpriteR,x,y,null);
        }else if(objectHandler.isUp()){
            g.drawImage(playerSpriteU,x,y,null);
        }else{
            g.drawImage(playerSpriteD,x,y,null);
        }
//        //top
//        g.setColor(Color.cyan);
//        g.drawRect(x, y, 32, 5);
//        //right
//        g.setColor(Color.blue);
//        g.drawRect(x+27, y, 5, 32);
//        //left
//        g.setColor(Color.orange);
//        g.drawRect(x , y, 5, 32);
//        //bot
//        g.setColor(Color.pink);
//        g.drawRect(x, y+27, 32, 5);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
    @Override
    public Rectangle getTopBounds(int offset) {
        return new Rectangle(x, y-offset, 32, 5);
    }

    @Override
    public Rectangle getRightBounds(int offset) {
        return new Rectangle(x+offset+27, y, 5, 32);
    }

    @Override
    public Rectangle getLeftBounds(int offset) {
        return new Rectangle(x - offset, y, 5, 32);
    }

    @Override
    public Rectangle getBotBounds(int offset) {
        return new Rectangle(x, y+27+ offset, 32, 5);
    }

    private void collisionWithWall() {
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.BLOCK) {
                if (getLeftBounds(1).intersects(tempObject.getRightBounds(0))) {
                    x += velocityX *= -1;
                }
                if (getRightBounds(1).intersects(tempObject.getLeftBounds(0))) {
                    x += velocityX *= -1;
                }
                if (getTopBounds(1).intersects(tempObject.getBotBounds(0))) {
                    y += velocityY *= -1;
                }
                if (getBotBounds(1).intersects(tempObject.getTopBounds(0))) {
                    y += velocityY *= -1;
                }
            }
        }
    }
    private void playerMovement(){
        if(objectHandler.isLeft())  velocityX = -speed;
        if(objectHandler.isRight()) velocityX = speed;
        else if(!(objectHandler.isLeft()|| objectHandler.isRight())) velocityX = 0;
        if(objectHandler.isUp())  velocityY = -speed;
        if(objectHandler.isDown())  velocityY = speed;
        else if(!(objectHandler.isUp()||objectHandler.isDown())) velocityY = 0;

    }
}


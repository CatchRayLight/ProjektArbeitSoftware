package gameDA.objects.model;


import gameDA.config.output.SpriteSheet;
import gameDA.objects.Animation;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;
import java.awt.*;

import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler objectHandler;
    private boolean onPlanet;
    private BufferedImage playerSpaceSR;
    private BufferedImage playerSpaceSL;
    private BufferedImage playerSpaceSU;
    private BufferedImage playerSpaceSD;
    private int speed = 5;
    private BufferedImage[] playerOnPlanetL = new BufferedImage[3];
    private BufferedImage[] playerOnPlanetR = new BufferedImage[3];
    private Animation animL;
    private Animation animR;


    public Player(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler, boolean onPlanet) {
        super(x, y, id,spriteSheet);
        this.objectHandler = objectHandler;
        this.onPlanet = onPlanet;
        //78
        playerSpaceSR = spriteSheet.getImage(7,8,32,32);
        playerSpaceSL = spriteSheet.getImage(8,8,32,32);
        playerSpaceSU = spriteSheet.getImage(7,7,32,32);
        playerSpaceSD = spriteSheet.getImage(8,7,32,32);
        playerOnPlanetR[0] = spriteSheet.getImage(1,6,32,32); //R1
        playerOnPlanetL[0] = spriteSheet.getImage(2,6,32,32); //L1
        playerOnPlanetR[1] = spriteSheet.getImage(3,6,32,32); //R2
        playerOnPlanetR[2] = spriteSheet.getImage(4,6,32,32); //R3
        playerOnPlanetL[1] = spriteSheet.getImage(5,6,32,32); //L2
        playerOnPlanetL[2] = spriteSheet.getImage(6,6,32,32); //L3
        animL = new Animation(6, playerOnPlanetL);
        animR = new Animation(6, playerOnPlanetR);
    }

    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        collisionWithWall();
        playerMovement();
        animL.runAnimation();
        animR.runAnimation();

    }


    @Override
    public void render(Graphics g) {
        if(!(velocityX == 0 && velocityY ==0)) {
            if ('L' == objectHandler.getDirection()) {
                if (onPlanet) animL.drawAnimation(g, x, y, 0);
                else
                    g.drawImage(playerSpaceSL, x, y, null);
            } else if ('R' == objectHandler.getDirection()) {
                if (onPlanet) animR.drawAnimation(g, x, y, 0);
                else
                    g.drawImage(playerSpaceSR, x, y, null);
            } else if ('U' == objectHandler.getDirection()) {
                if (onPlanet)g.drawImage(playerOnPlanetR[0],x,y,null);
                else
                    g.drawImage(playerSpaceSU, x, y, null);
            }else
                if (onPlanet)g.drawImage(playerOnPlanetL[0],x,y,null);
                else
                    g.drawImage(playerSpaceSD, x, y, null);
        }else{
            if(!onPlanet)g.drawImage(playerSpaceSD,x,y,null);
            else
                g.drawImage(playerOnPlanetR[0],x,y,null);
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
    public boolean isOnPlanet() {
        return onPlanet;
    }

    public void setOnPlanet(boolean onPlanet) {
        this.onPlanet = onPlanet;
    }
    private void collisionWithWall() {
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.BLOCK) {
                if (getLeftBounds(5).intersects(tempObject.getRightBounds(0))) {
                    x += velocityX *= -1;
                }
                if (getRightBounds(5).intersects(tempObject.getLeftBounds(0))) {
                    x += velocityX *= -1;
                }
                if (getTopBounds(5).intersects(tempObject.getBotBounds(0))) {
                    y += velocityY *= -1;
                }
                if (getBotBounds(5).intersects(tempObject.getTopBounds(0))) {
                    y += velocityY *= -1;
                }
            }
        }
    }
    private void playerMovement(){
        if(objectHandler.isLeft())  velocityX = -speed;
        if(objectHandler.isRight()) velocityX = speed;
        else if(!(objectHandler.isLeft() || objectHandler.isRight())) velocityX = 0;
        if(objectHandler.isUp())  velocityY = -speed;
        if(objectHandler.isDown())  velocityY = speed;
        else if(!(objectHandler.isUp()||objectHandler.isDown())) velocityY = 0;

    }
}


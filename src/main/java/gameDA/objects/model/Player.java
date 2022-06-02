package gameDA.objects.model;


import gameDA.config.output.SpriteSheet;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;
import gameDA.objects.Physics;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler objectHandler;
    Physics physics = new Physics();
    private BufferedImage playerSpriteR;
    private BufferedImage playerSpriteL;
    private BufferedImage playerSpriteU;
    private BufferedImage playerSpriteD;


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


        if(physics.collision(objectHandler,getBounds())){
            x += velocityX * -1;
            y += velocityY * -1;
        }
        velocityY = physics.playerMovementY(objectHandler,velocityY);
        velocityX = physics.playerMovementX(objectHandler,velocityX);
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


    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+8,y+8,16,16);
    }
}


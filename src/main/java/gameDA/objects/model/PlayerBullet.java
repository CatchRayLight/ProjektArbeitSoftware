package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.objects.Animation;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Atilla Ipek
 */
public class PlayerBullet extends GameObject {
    private final BufferedImage[] playerBulletV = new BufferedImage[2];
    private final BufferedImage[] playerBulletH = new BufferedImage[2];

    private ObjectHandler objectHandler;

    private Animation animationV;
    private Animation animationH;
    private char direction;

     public PlayerBullet(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler,int bulletSpeed,char direction) {
         super(x, y, id, spriteSheet);
         this.objectHandler = objectHandler;
         this.direction = direction;
         //right // left
         playerBulletV[0] = spriteSheet.getImage(9,9,32,32);
         playerBulletV[1] = spriteSheet.getImage(10,9,32,32);
         //top//Down
         playerBulletH[0] = spriteSheet.getImage(9,10,32,32);
         playerBulletH[1] = spriteSheet.getImage(10,10,32,32);
         animationV = new Animation(3,playerBulletV);
         animationH = new Animation(3,playerBulletH);
         switch (direction){
            case 'U':
                velocityX = 0;
                velocityY = -bulletSpeed;
                break;
            case 'D':
                velocityX = 0;
                velocityY = bulletSpeed;
                break;
            case 'R':
                velocityX = bulletSpeed;
                velocityY = 0;
                break;
            case 'L':
                velocityX = -bulletSpeed;
                velocityY = 0;
                break;
        }
    }

    @Override
    public void update() {
         if (Game.gamestate.equals(Gamestate.INGAME)) {
             x += velocityX;
             y += velocityY;
         }
        animationH.runAnimation();
        animationV.runAnimation();
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.BLOCK) {
                if(getBounds().intersects(tempObject.getBounds())){
                    objectHandler.removeObj(this);
//                    objectHandler.removeObj(tempObject);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        switch (direction){
            case 'U':case 'D':
                animationH.drawAnimation(g,x,y,0);
                break;
            case 'R':case 'L':
                animationV.drawAnimation(g,x,y,0);
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,10,10);
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

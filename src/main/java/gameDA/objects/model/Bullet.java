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
public class Bullet extends GameObject {

    private final ObjectHandler objectHandler;

    private Animation playerAnimationBulletV;
    private Animation playerAnimationBulletH;

    private Animation enemyAnimationBulletV;

    private Animation enemyAnimationBulletH;
    private final char direction;
    private final boolean player;

     public Bullet(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler, int bulletSpeed, char direction,boolean player) {
         super(x, y, id, spriteSheet);
         this.objectHandler = objectHandler;
         this.direction = direction;
         this.player = player;
         //right // left
         if(player) {
             BufferedImage[] playerBulletV = new BufferedImage[2];
             playerBulletV[0] = spriteSheet.getImage(9, 9, 32, 32);
             playerBulletV[1] = spriteSheet.getImage(10, 9, 32, 32);
             //top//Down
             BufferedImage[] playerBulletH = new BufferedImage[2];
             playerBulletH[0] = spriteSheet.getImage(9, 10, 32, 32);
             playerBulletH[1] = spriteSheet.getImage(10, 10, 32, 32);

             playerAnimationBulletV = new Animation(3, playerBulletV);
             playerAnimationBulletH = new Animation(3, playerBulletH);

         }else {
             BufferedImage[] enemyBulletH = new BufferedImage[2];
             enemyBulletH[0] = spriteSheet.getImage(11, 9, 32, 32);
             enemyBulletH[1] = spriteSheet.getImage(11, 10, 32, 32);

             BufferedImage[] enemyBulletV = new BufferedImage[2];
             enemyBulletV[0] = spriteSheet.getImage(12, 9, 32, 32);
             enemyBulletV[1] = spriteSheet.getImage(12, 10, 32, 32);

             enemyAnimationBulletV = new Animation(3,enemyBulletV);
             enemyAnimationBulletH = new Animation( 3, enemyBulletH);
         }
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
        if (player) {
            playerAnimationBulletH.runAnimation();
            playerAnimationBulletV.runAnimation();
        }else{
            enemyAnimationBulletV.runAnimation();
            enemyAnimationBulletH.runAnimation();
        }
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.BLOCK) {
                if(getBounds().intersects(tempObject.getBounds())){
                    if(!Game.getGame().isBossLvl())objectHandler.removeObj(this);
                }
            }
            if(Game.getGame().isBossLvl() && (y > 900 || x < 0 || x > 1300)){
                objectHandler.removeObj(this);
            }
        }
    }
    @Override
    public void render(Graphics g) {
        switch (direction){
            case 'U':case 'D':
                if(player)playerAnimationBulletH.drawAnimation(g,x,y,0);
                else enemyAnimationBulletH.drawAnimation(g,x,y,0);
                break;
            case 'R':case 'L':
                if(player)playerAnimationBulletV.drawAnimation(g,x,y,0);
                else enemyAnimationBulletV.drawAnimation(g,x,y,0);
                break;
        }
        /*

        //hitbox
        g.setColor(Color.red);
        g.drawRect(x+10,y+10,10,10);

         */
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+10,y+10,10,10);
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

    public char getDirection() {
        return direction;
    }

    public boolean isPlayer() {
        return player;
    }
}

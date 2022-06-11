package gameDA.objects.model;

import gameDA.config.output.SpriteSheet;
import gameDA.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpaceEnemy extends GameObject{
    private final Animation animationEnemy;
    private ObjectHandler objectHandler;

    private final Healthbar enemyHealthbar;
    private int hp;

    private Player player;
    private final Animation explosionAnimation;


    public SpaceEnemy(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler,int hp) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;
        this.hp = hp;
        BufferedImage[] enemyInSpace = new BufferedImage[6];
        enemyInSpace[0] = spriteSheet.getImage(1,7,32,32);
        enemyInSpace[1] = spriteSheet.getImage(2,7,32,32);
        enemyInSpace[2] = spriteSheet.getImage(3,7,32,32);
        enemyInSpace[3] = spriteSheet.getImage(4,7,32,32);
        enemyInSpace[4] = spriteSheet.getImage(5,7,32,32);
        enemyInSpace[5] = spriteSheet.getImage(6,7,32,32);
        animationEnemy = new Animation(10, enemyInSpace);
        enemyHealthbar = new Healthbar(hp);
        BufferedImage[] explosionImg = new BufferedImage[9];
        explosionImg[0] = spriteSheet.getImage(9,11,32,32);
        explosionImg[1] = spriteSheet.getImage(10,11,32,32);
        explosionImg[2] = spriteSheet.getImage(11,11,32,32);
        explosionImg[3] = spriteSheet.getImage(12,11,32,32);
        explosionImg[4] = spriteSheet.getImage(13,11,32,32);
        explosionImg[5] = spriteSheet.getImage(14,11,32,32);
        explosionImg[6] = spriteSheet.getImage(15,11,32,32);
        explosionImg[7] = spriteSheet.getImage(16,11,32,32);
        explosionImg[8] = spriteSheet.getImage(17,11,32,32);
        explosionAnimation = new Animation(1, explosionImg);
    }

    @Override
    public void update() {
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if(tempObject.getId() == ObjectID.PLAYER){
                player = (Player)tempObject;
            }
            if (tempObject.getId() == ObjectID.BULLET) {
                if(getBounds().intersects(tempObject.getBounds())){
                    setHp(Math.min(getHp()-player.getBulletDmg(),100));
                    enemyHealthbar.setHp(getHp());
                    if(getHp() == 0){
                        //find way to animate
                        explosionAnimation.runAnimation();
                        objectHandler.removeObj(this);
                    }
                    objectHandler.removeObj(tempObject);
                }
            }
        }
        animationEnemy.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        animationEnemy.drawAnimation(g,x,y,0);
        explosionAnimation.drawAnimation(g,x,y,0);
        enemyHealthbar.render(g,x,y,-16,-18,70,12);
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
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
}


package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpaceEnemy extends GameObject{
    private final Animation animationEnemy;
    private final ObjectHandler objectHandler;

    private final Healthbar enemyHealthbar;
    private int hp;

    private Player player;
    private int bulletSpeed;
    private  int coolDownCounter;
    private int bulletCooldown;

    private int bulletDmg;

    public SpaceEnemy(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler,int hp,
                      int bulletSpeed,int bulletCooldown,int bulletDmg) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;
        this.hp = hp;
        this.bulletSpeed = bulletSpeed;
        this.bulletCooldown = bulletCooldown;
        this.bulletDmg = bulletDmg;
        BufferedImage[] enemyInSpace = new BufferedImage[6];
        enemyInSpace[0] = spriteSheet.getImage(1,7,32,32);
        enemyInSpace[1] = spriteSheet.getImage(2,7,32,32);
        enemyInSpace[2] = spriteSheet.getImage(3,7,32,32);
        enemyInSpace[3] = spriteSheet.getImage(4,7,32,32);
        enemyInSpace[4] = spriteSheet.getImage(5,7,32,32);
        enemyInSpace[5] = spriteSheet.getImage(6,7,32,32);
        animationEnemy = new Animation(20, enemyInSpace);
        enemyHealthbar = new Healthbar(hp);
    }

    @Override
    public void update() {
        bulletShooting();
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                player = (Player) tempObject;
            }
            if (tempObject.getId() == ObjectID.PLAYERBULLET) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    setHp(Math.min(getHp() - player.getBulletDmg(), 100));
                    if (getHp() <= 0) {
                        enemyHealthbar.setHp(0);
                        objectHandler.removeObj(this);
                        player.setPlayerCoins(player.getPlayerCoins()+5);
                        player.getPlayerHealthbar().setPlayerCoins(player.getPlayerCoins());
                    }
                    enemyHealthbar.setHp(getHp());
                    objectHandler.removeObj(tempObject);
                }
            }
        }
        animationEnemy.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        if(!(Game.getGame().getLvLInt() == 2|| Game.getGame().getLvLInt() == 5|| Game.getGame().getLvLInt() == 8))animationEnemy.drawAnimation(g,x,y,0);
        enemyHealthbar.render(g,x,y,-16,-18,70,12);
    /*
        //Hitbox-PlayerDetection
        //top
        int offset = 100;
        g.setColor(Color.cyan);
        g.drawRect(x-(offset/2), y-offset, (32+offset), 5);
        //right
        g.setColor(Color.blue);
        g.drawRect((x+27)+offset, y-(offset/2), 5, 32+offset);
        //left
        g.setColor(Color.orange);
        g.drawRect(x-offset, y-(offset/2), 5, 32+offset);
        //bot
        g.setColor(Color.pink);
        g.drawRect(x-(offset/2), y+27+offset, 32+offset, 5);

        SpaceEnemy Hitbox
        g.setColor(Color.red);
        g.drawRect(x,y,32,32);

     */
    }
    //durch abfrage der Kanten Rechts, Links,Unten,Oben und ob sie kollidieren mit dem Spieler, wird eine instanz der Bullet gemacht mit angegebener Richtung
    private void bulletShooting() {
        coolDownCounter++;
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                if (getLeftBounds(100).intersects(tempObject.getBounds())) {
                    if (coolDownCounter > bulletCooldown) {
                        objectHandler.addObj(new Bullet(getX(), getY(), ObjectID.ENEMYBULLET,
                                spriteSheet, objectHandler, bulletSpeed, 'L', false));
                        coolDownCounter = 0;
                    }
                }
                if (getRightBounds(100).intersects(tempObject.getBounds())) {
                    if (coolDownCounter > bulletCooldown) {
                        objectHandler.addObj(new Bullet(getX(), getY(), ObjectID.ENEMYBULLET,
                                spriteSheet, objectHandler, bulletSpeed, 'R', false));
                        coolDownCounter = 0;
                    }
                }
                if (getTopBounds(100).intersects(tempObject.getBounds())) {
                    if (coolDownCounter > bulletCooldown) {
                        objectHandler.addObj(new Bullet(getX(), getY(), ObjectID.ENEMYBULLET,
                                spriteSheet, objectHandler, bulletSpeed, 'U', false));
                        coolDownCounter = 0;
                    }
                }
                if (getBotBounds(100).intersects(tempObject.getBounds())) {
                    if (coolDownCounter > bulletCooldown) {
                        objectHandler.addObj(new Bullet(getX(), getY(), ObjectID.ENEMYBULLET,
                                spriteSheet, objectHandler, bulletSpeed, 'D', false));
                        coolDownCounter = 0;
                    }
                }
            }
        }
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    @Override
    public Rectangle getTopBounds(int offset) {
        return new Rectangle(x-(offset/2), y-offset, 32+offset, 50);
    }

    @Override
    public Rectangle getRightBounds(int offset) {
        return new Rectangle((x+27)+offset, y-(offset/2), 50, 32+offset);
    }

    @Override
    public Rectangle getLeftBounds(int offset) {
        return new Rectangle(x-offset, y-(offset/2), 50, 32+offset);
    }

    @Override
    public Rectangle getBotBounds(int offset) {
        return new Rectangle(x-(offset/2), y+27+offset, 32+offset, 50);
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getBulletCooldown() {
        return bulletCooldown;
    }

    public void setBulletCooldown(int bulletCooldown) {
        this.bulletCooldown = bulletCooldown;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public SpaceEnemy setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
        return this;
    }

    public int getBulletDmg() {
        return bulletDmg;
    }

    public void setBulletDmg(int bulletDmg) {
        this.bulletDmg = bulletDmg;
    }
}


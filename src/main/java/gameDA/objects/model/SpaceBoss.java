package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.submenus.DialogueMenu;
import gameDA.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpaceBoss extends GameObject {
    private ObjectHandler objectHandler;
    private final Animation bossAnimationEnemy;
    private final Animation bossAnimationEnemy2;
    private final Animation bossAnimationEnemy3;
    private Healthbar bossHealtbar;
    private int hp;
    private Player player;
    private final LvLHandler lvLHandler;
    private int bulletSpeed;
    private int bulletCooldown;
    private int bulletDmg;
    private int counter = 0;
    public SpaceBoss(int x, int y, ObjectID id, SpriteSheet spriteSheet,ObjectHandler objectHandler, int hp,
                     int bulletSpeed,int bulletCooldown,int bulletDmg) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;
        this.hp = hp;
        this.bulletCooldown = bulletCooldown;
        this.bulletDmg = bulletDmg;
        this.bulletSpeed = bulletSpeed;
        BufferedImage[] bossEnemyInSpace = new BufferedImage[2];
        bossEnemyInSpace[0] = spriteSheet.getImage(13,3,64,64);
        bossEnemyInSpace[1] = spriteSheet.getImage(15,3,64,64);
        bossAnimationEnemy = new Animation(20, bossEnemyInSpace);
        bossAnimationEnemy2 = new Animation(20, bossEnemyInSpace);
        bossAnimationEnemy3 = new Animation(20, bossEnemyInSpace);
        bossHealtbar = new Healthbar(hp);
        lvLHandler = new LvLHandler();
    }

    @Override
    public void update() {
        if(Game.getGame().isBossLvl()) {
            counter++;
            if(counter > bulletCooldown) {
                bulletShooting();
                counter = 0;
            }
            if (Game.getGame().getLvLInt() == 2)bossAnimationEnemy.runAnimation();
            if (Game.getGame().getLvLInt() == 5)bossAnimationEnemy2.runAnimation();
            if (Game.getGame().getLvLInt() == 8)bossAnimationEnemy3.runAnimation();
            for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
                GameObject tempObject = objectHandler.gameObjects.get(i);
                if (tempObject.getId() == ObjectID.PLAYER) {
                    player = (Player) tempObject;
                }
                if (tempObject.getId() == ObjectID.PLAYERBULLET) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        setHp(Math.min(getHp() - player.getBulletDmg(), hp));
                        bossHealtbar.setHp(getHp());
                        if (getHp() <= 0) {
                            objectHandler.removeObj(this);
                            player.setPlayerCoins(player.getPlayerCoins() + 30);
                            player.getPlayerHealthbar().update();
                            //if won
                            Game.getGame().setGamestate(Gamestate.INMENU);
                            String[][] sampleDialogue = new String[][]{{"Text"}, {"Text", "line 2", "line 3"}};
                            Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                            lvLHandler.nextLvL(objectHandler);
                        }
                        objectHandler.removeObj(tempObject);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if(Game.getGame().isBossLvl()) {
            if (Game.getGame().getLvLInt() == 2) bossAnimationEnemy.drawAnimation(g, x, y, 0);
            if (Game.getGame().getLvLInt() == 5) bossAnimationEnemy2.drawAnimation(g, x, y, 0);
            if (Game.getGame().getLvLInt() == 8) bossAnimationEnemy3.drawAnimation(g, x, y, 0);
            bossHealtbar.render(g,x-300,y-50,-16,-18,70,12);
            //draw bullets
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x-1000,y,2000,32);
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

    public SpaceBoss setHp(int hp) {
        this.hp = hp;
        return this;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public SpaceBoss setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
        return this;
    }

    public int getBulletCooldown() {
        return bulletCooldown;
    }

    public SpaceBoss setBulletCooldown(int bulletCooldown) {
        this.bulletCooldown = bulletCooldown;
        return this;
    }

    public int getBulletDmg() {
        return bulletDmg;
    }

    public SpaceBoss setBulletDmg(int bulletDmg) {
        this.bulletDmg = bulletDmg;
        return this;
    }

    private void bulletShooting() {
        int test = (int) (Math.random() * 10);
        switch (test) {
            case 0:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random())), getY()-100, ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 1:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))-100, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 2:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))+200, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 3:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))-200, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 4:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))+300, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 5:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))-400, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 6:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))+400, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 7:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))+500, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 8:
                objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))+600, getY(), ObjectID.ENEMYBULLET,
                        spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
            case 9:
            objectHandler.addObj(new Bullet((int)(getX()* (Math.random()))-600, getY(), ObjectID.ENEMYBULLET,
                    spriteSheet, objectHandler, bulletSpeed, 'D', false));
                break;
        }
    }
}

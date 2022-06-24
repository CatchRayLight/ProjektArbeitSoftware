package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.submenus.DialogueMenu;
import gameDA.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SpaceBoss extends GameObject {
    private final ObjectHandler objectHandler;
    private final Animation bossAnimationEnemy;
    private final Healthbar bossHealtbar;
    private int hp;
    private final Player player;
    private final LvLHandler lvLHandler;
    private int bulletSpeed;
    private int bulletCooldown;
    private int bulletDmg;
    private int counter;
    private final Random random;
    private int offset = 0;
    public SpaceBoss(int x, int y, ObjectID id, SpriteSheet spriteSheet,ObjectHandler objectHandler, int hp,
                     int bulletSpeed,int bulletCooldown,int bulletDmg) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;
        this.hp = hp;
        this.bulletCooldown = bulletCooldown;
        this.bulletDmg = bulletDmg;
        this.bulletSpeed = bulletSpeed;
        BufferedImage[] bossEnemyInSpace = new BufferedImage[2];
        if(Game.getGame().getLvLInt() == 2) {
            bossEnemyInSpace[0] = spriteSheet.getImage(13, 3, 64, 64);
            bossEnemyInSpace[1] = spriteSheet.getImage(15, 3, 64, 64);
        }
        if(Game.getGame().getLvLInt() == 5) {
            bossEnemyInSpace[0] = spriteSheet.getImage(17, 3, 64, 64);
            bossEnemyInSpace[1] = spriteSheet.getImage(19, 3, 64, 64);
        }
        if(Game.getGame().getLvLInt() == 8) {
            offset = -320;
            bossEnemyInSpace[0] = spriteSheet.getImage(15, 1, 64, 64);
            bossEnemyInSpace[1] = spriteSheet.getImage(17, 1, 64, 64);
        }
        bossAnimationEnemy = new Animation(20, bossEnemyInSpace);
        bossHealtbar = new Healthbar(hp);
        lvLHandler = new LvLHandler();
        random = new Random();
        counter = 0;
        player = objectHandler.getPlayer();
    }

    @Override
    public void update() {
        if(Game.getGame().isBossLvl()) {
            bulletShooting(offset);
            bossAnimationEnemy.runAnimation();
            for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
                GameObject tempObject = objectHandler.gameObjects.get(i);
                if (tempObject.getId() == ObjectID.PLAYERBULLET) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        setHp(Math.min(getHp() - player.getBulletDmg(), hp));
                        bossHealtbar.setHp(getHp());
                        if (getHp() <= 0) {
                            objectHandler.removeObj(this);
                            player.setPlayerCoins(player.getPlayerCoins() + 30);
                            player.getPlayerHealthbar().update();
                            //if won

                            String[][] sampleDialogue = new String[][]{{"Endlich sind ich und mein Pokal wieder vereint!", "Jetzt kann ich beruhigt wieder meine Getränke tornadon."}};
                            if(Game.getGame().getLvLInt() == 2) {
                                sampleDialogue = new String[][]{{"Ein Teil meines Pokales hab ich jetzt endlich wieder. Nur noch 2 weitere"}};
                            }
                            if(Game.getGame().getLvLInt() == 5) {
                                sampleDialogue = new String[][]{{"Noch ein Teil und mein Pokal ist wieder komplett. Warte nur du Alien, dich krieg ich auch noch!"}};
                            }
                            Game.getGame().setGamestate(Gamestate.INMENU);
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
            bossAnimationEnemy.drawAnimation(g, x, y, 0);
            bossHealtbar.render(g,x-333,y-50,-16,-18,70,12);
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

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public int getBulletCooldown() {
        return bulletCooldown;
    }

    public void setBulletCooldown(int bulletCooldown) {
        this.bulletCooldown = bulletCooldown;
    }

    public int getBulletDmg() {
        return bulletDmg;
    }

    public void setBulletDmg(int bulletDmg) {
        this.bulletDmg = bulletDmg;
    }

    //lässt den Spaceboss random im angegebenen bound schießen
    private void bulletShooting(int offset){
        counter++;
        if(counter > bulletCooldown) {
            objectHandler.addObj(new Bullet(getX() + random.nextInt(1200) - 600 + offset, getY(), ObjectID.ENEMYBULLET,
                    spriteSheet, objectHandler, bulletSpeed, 'D', false));
            counter = 0;
        }
    }
}

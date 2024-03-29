package gameDA.objects.model;


import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.submenus.DeathMenu;
import gameDA.objects.*;

import java.awt.*;

import java.awt.image.BufferedImage;

import static gameDA.config.input.KeyListener.frameChange;

public class Player extends GameObject {
    private boolean onPlanet;
    private final BufferedImage playerSpaceSR;
    private final BufferedImage playerSpaceSL;
    private final BufferedImage playerSpaceSU;
    private final BufferedImage playerSpaceSD;
    private int unitMove;

    private final ObjectHandler objectHandler;
    private final BufferedImage[] playerOnPlanetL = new BufferedImage[3];
    private final BufferedImage[] playerOnPlanetR = new BufferedImage[3];
    private  final BufferedImage viewIMG;
    private final Animation animL;
    private final Animation animR;

    public static Healthbar playerHealthbar = null;

    private int hp;
    private int ammo;
    private int fuel;
    private  int couldownCounter;
    private int bulletSpeed;
    private int cooldownBullet;
    private int offsetS;

    private int bulletCost;

    private int bulletDmg;

    private int playerCoins;

    private SpaceEnemy spaceEnemy;

    public Player(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler, boolean onPlanet,
                  Camera camera, int hp, int ammo, int fuel, int bulletSpeed, int cooldownBullet,int bulletCost,
                  int bulletDmg,int playerCoins) {
        super(x, y, id,spriteSheet);
        this.objectHandler = objectHandler;
        this.onPlanet = onPlanet;
        this.fuel = fuel;
        this.ammo = ammo;
        this.hp = hp;
        this.cooldownBullet = cooldownBullet;
        this.bulletSpeed = bulletSpeed;
        this.bulletCost = bulletCost;
        this.bulletDmg = bulletDmg;
        this.playerCoins = playerCoins;
        BufferedImageLoader loader = new BufferedImageLoader();
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
        playerHealthbar = new Healthbar(spriteSheet, hp,ammo,fuel, camera,playerCoins);
        viewIMG = loader.loadImage("/textures/viewtest.png");
        couldownCounter = 0;
    }
    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        if(isOnPlanet()) {
            animL.runAnimation();
            animR.runAnimation();
            unitMove = 5;
        } else {
            unitMove = 8;
        }
        motionCancelCollision(unitMove,ObjectID.BLOCK);
        playerMovement();
        playerShooting();
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if(tempObject.getId() == ObjectID.ENEMY){
                spaceEnemy = (SpaceEnemy) tempObject;
            }
            if (tempObject.getId() == ObjectID.ENEMYBULLET) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if(getHp() > 0){
                        setHp((getHp() - spaceEnemy.getBulletDmg()));
                        objectHandler.removeObj(tempObject);
                        playerHealthbar.setHp(getHp());
                    }
                }
            }
        }
        if ((getHp() <= 0) || (getFuel() <= 0)) {
            playerHealthbar.setHp(0);
            playerHealthbar.setFuel(0);
            Game.getGame().setGamestate(Gamestate.INMENU);
            Game.getGame().getMenuHandler().setCurrentMenu(new DeathMenu());
        }
    }
    @Override
    public void render(Graphics g) {
        if((!isOnPlanet() &&!(Game.getGame().getLvLInt() == 2 || Game.getGame().getLvLInt() == 5 ||
                Game.getGame().getLvLInt() == 8) )&&!(Game.getGame().getLvLInt() >= 9)){
            g.drawImage(viewIMG,x- (viewIMG.getWidth() /2) + 15,y - (viewIMG.getHeight()/2) + 20,null);
        }
        playerHealthbar.render(g);
        if(!frameChange){
            if (onPlanet) g.drawImage(playerOnPlanetL[0], x, y, null);
            else
                g.drawImage(playerSpaceSD, x, y, null);
        }
        switch (objectHandler.getDirection()){
            case 'L':
                if (onPlanet) animL.drawAnimation(g, x, y, 0);
                else
                    g.drawImage(playerSpaceSL, x, y, null);
                break;
            case 'R':
                if (onPlanet) animR.drawAnimation(g,x,y,0);
                else
                    g.drawImage(playerSpaceSR, x, y, null);
                break;
            case 'D':
                if (onPlanet) g.drawImage(playerOnPlanetL[0], x, y, null);
                else
                    g.drawImage(playerSpaceSD, x, y, null);
                break;
            case 'U':
                if (onPlanet) g.drawImage(playerOnPlanetR[0], x, y, null);
                else
                    g.drawImage(playerSpaceSU, x, y, null);
                break;
        }
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
   public void toggleOnPlanet(){
        onPlanet = !onPlanet;
    }

   public int getHp() {
        return hp;
   }

   public void setHp(int hp) {
        this.hp = hp;
   }

   public int getAmmo() {
        return ammo;
   }

   public int getPlayerCoins() {
        return playerCoins;
   }

   public void setPlayerCoins(int playerCoins) {
        this.playerCoins = playerCoins;
   }

   public void setAmmo(int ammo) {
        this.ammo = ammo;
   }

   public int getFuel() {
        return fuel;
   }

   public void setFuel(int fuel) {
        this.fuel = fuel;
   }

   public int getBulletSpeed() {
        return bulletSpeed;
   }

   public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
   }

   public int getUnitMove() {
        return unitMove;
   }

   public int getBulletDmg() {
        return bulletDmg;
   }

   public int getBulletCost() {
        return bulletCost;
   }

   public void setBulletCost(int bulletCost) {
        this.bulletCost = bulletCost;
   }

   public void setBulletDmg(int bulletDmg) {
        this.bulletDmg = bulletDmg;
   }
    public int getCooldownBullet() {
        return cooldownBullet;
    }

    public void setCooldownBullet(int cooldownBullet) {
        this.cooldownBullet = cooldownBullet;
    }
    //bekommt die Spieler Lebensbar
    public Healthbar getPlayerHealthbar(){
        return playerHealthbar;
    }
    //Vermeidet Kollsion durch abfrage des anderen Objectes des parameters
    private void motionCancelCollision(int offset, ObjectID objectID) {
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == objectID) {
                if (getLeftBounds(offset).intersects(tempObject.getRightBounds(0))) {
                    x += velocityX *= -1;
                }
                if (getRightBounds(offset).intersects(tempObject.getLeftBounds(0))) {
                    x += velocityX *= -1;
                }
                if (getTopBounds(offset).intersects(tempObject.getBotBounds(0))) {
                    y += velocityY *= -1;
                }
                if (getBotBounds(offset).intersects(tempObject.getTopBounds(0))) {
                    y += velocityY *= -1;
                }
                else if(getBounds().intersects(tempObject.getBounds())) {
                    x += velocityX *= -1;
                    y += velocityY *= -1;
                    System.out.println("o.o");
                }
            }
        }
    }
    //hierdurch wird der Spieler bewegt, der  Keylistener hört Tastatur und diese Methode agiert dadurch und bestimmt velX velY
   private void playerMovement(){
       if(!(Game.gamestate.equals(Gamestate.INGAME))){
           velocityX = 0;
           velocityY = 0;
           objectHandler.setLeft(false);
           objectHandler.setRight(false);
           objectHandler.setUp(false);
           objectHandler.setDown(false);
           objectHandler.setSpace(false);
       }else {
           if (objectHandler.isLeft()){ velocityX = -unitMove;}
           if (objectHandler.isRight()){ velocityX = unitMove;}
           else if (!(objectHandler.isLeft() || objectHandler.isRight())) {velocityX = 0;}
           if (objectHandler.isUp()) {velocityY = -unitMove;}
           if (objectHandler.isDown()) {velocityY = unitMove;}
           else if (!(objectHandler.isUp() || objectHandler.isDown())) {velocityY = 0;}
        }
       if((velocityY != 0) || (velocityX != 0)){
           if (!isOnPlanet()) {
               setFuel(getFuel() - 4 - Game.getGame().getLvLInt());
               playerHealthbar.setFuel(getFuel());
           }
       }
    }
    //Diese Methode kreiert eine instance einer Bullet und gibt die werte die die Kugel haben soll mit
   private void playerShooting(){
       if(!onPlanet) {
           couldownCounter++;
           if (objectHandler.isSpace() && couldownCounter > cooldownBullet) {
               if (getAmmo() > 0 || Game.getGame().isBossLvl()) {
                   objectHandler.addObj(new Bullet(getX(), getY(), ObjectID.PLAYERBULLET,
                           spriteSheet, objectHandler, bulletSpeed, objectHandler.getDirection(),true));
                   if(!Game.getGame().isBossLvl()) {
                       setAmmo(getAmmo() - getBulletCost());
                       playerHealthbar.setAmmo(getAmmo());
                   }
               }
               couldownCounter = 0;
           }
       }
   }
}


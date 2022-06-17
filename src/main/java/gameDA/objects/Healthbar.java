package gameDA.objects;

import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.Game;
import gameDA.objects.model.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Healthbar{
    private BufferedImage playerHealthbar;
    private BufferedImage playerCoin;
    private int x;
    private int y;
    private int hp;
    private int ammo;
    private int fuel;
    private int playerCoins;
    private Camera camera;


    public Healthbar(SpriteSheet spriteSheet, int hp, int ammo, int fuel, Camera camera,int playerCoins) {
        this.camera = camera;
        this.hp = hp;
        this.ammo = ammo;
        this.fuel = fuel;
        this.playerCoins = playerCoins;
        playerHealthbar = spriteSheet.getImage(7, 3, 192, 96);
        playerCoin = spriteSheet.getImage(14,2,32,32);
    }
    public Healthbar(int hp) {
        this.hp = hp;
    }

    public void update() {

    }
    public void render(Graphics g) {
        g.setFont(Objects.requireNonNull(loadFont()).deriveFont(24f));

        g.setColor(Color.red);
        g.fillRect(camera.getX() + 43, camera.getY() + Game.SCREEN_HEIGHT - 150, (getHp()* 156) / 100, 20);
        g.drawString(String.valueOf(getHp()),camera.getX() + 150, camera.getY() + Game.SCREEN_HEIGHT-150);

        g.setColor(Color.orange);
        g.fillRect(camera.getX() + 43, camera.getY() + Game.SCREEN_HEIGHT - 113, (getAmmo() * 156) / 100, 20);
        g.drawString(String.valueOf(getAmmo()),camera.getX() + 150, camera.getY() + Game.SCREEN_HEIGHT-113);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(camera.getX() + 43, camera.getY() + Game.SCREEN_HEIGHT - 75, (getFuel()) / 100, 20);
        g.drawImage(playerHealthbar, camera.getX() + 10, camera.getY() + Game.SCREEN_HEIGHT - 150, null);
        g.drawString(String.valueOf(getFuel()/156),camera.getX() + 150, camera.getY() + Game.SCREEN_HEIGHT-75);

        g.setColor(Color.white);
        g.setFont(Objects.requireNonNull(loadFont()).deriveFont(30f));
        g.drawImage(playerCoin,camera.getX()+5,camera.getY()+ Game.SCREEN_HEIGHT - 190,null);
        g.drawString(String.valueOf(getPlayerCoins()),camera.getX()+37,camera.getY()+ Game.SCREEN_HEIGHT - 164);
    }
    public void render(Graphics g,int x,int y, int offsetX, int offsetY,int size,int height){
        g.setColor(Color.red);
        g.fillRect(x + offsetX, y + offsetY, hp * size / 100, height);
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

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getPlayerCoins() {
        return playerCoins;
    }

    public void setPlayerCoins(int playerCoins) {
        this.playerCoins = playerCoins;

    }
    private Font loadFont(){
        try {
            //create the font to use. Specify the size!
            return Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/font/ARCADECLASSIC.TTF")).deriveFont(30f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
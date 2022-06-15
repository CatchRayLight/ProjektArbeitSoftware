package gameDA.objects;

import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Healthbar{
    private BufferedImage healthbar;
    private int x;
    private int y;
    private int hp;
    private int ammo;
    private int fuel;
    private Camera camera;
    private boolean player;

    public Healthbar(SpriteSheet spriteSheet, int hp, int ammo, int fuel, Camera camera) {
        this.camera = camera;
        this.hp = hp;
        this.ammo = ammo;
        this.fuel = fuel;
        healthbar = spriteSheet.getImage(7, 3, 192, 96);
    }
    public Healthbar(int hp) {
        this.hp = hp;
    }

    public void update() {

    }
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(camera.getX() + 43, camera.getY() + Game.SCREEN_HEIGHT - 150, (hp * 156) / 100, 20);
        g.setColor(Color.orange);
        g.fillRect(camera.getX() + 43, camera.getY() + Game.SCREEN_HEIGHT - 113, (ammo * 156) / 100, 20);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(camera.getX() + 43, camera.getY() + Game.SCREEN_HEIGHT - 75, (fuel * 156) / 100, 20);
        g.drawImage(healthbar, camera.getX() + 10, camera.getY() + Game.SCREEN_HEIGHT - 150, null);
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
}
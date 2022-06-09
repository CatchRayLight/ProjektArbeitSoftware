package gameDA.objects;

import gameDA.config.output.SpriteSheet;
import gameDA.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Healthbar {
    private BufferedImage healthbar;
    private SpriteSheet spriteSheet;
    private int x;
    private int y;
    private int hp;
    private int ammo;
    private int fuel;


    public Healthbar(int x, int y, SpriteSheet spriteSheet, int hp, int ammo, int fuel) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.ammo = ammo;
        this.fuel = fuel;
        this.spriteSheet = spriteSheet;
        healthbar = spriteSheet.getImage(7, 3, 192, 96);
    }

    public void update() {
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x+43,y+ Game.SCREEN_HEIGHT - 150,(hp * 156) / 100,20);

        g.setColor(Color.orange);
        g.fillRect(x+43,y+ Game.SCREEN_HEIGHT - 113,(ammo* 156) / 100,20);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x+43,y+ Game.SCREEN_HEIGHT - 75,(fuel * 156) / 100,20);
        g.drawImage(healthbar, x + 10, y+ Game.SCREEN_HEIGHT - 150, null);
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

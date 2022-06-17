package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.objects.Animation;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Walls extends GameObject {
    private final BufferedImage sprite;
    private final boolean onPlanet;

    public Walls(int x, int y, ObjectID id, SpriteSheet spriteSheet, boolean onPlanet) {
        super(x, y, id,spriteSheet);
        this.onPlanet = onPlanet;
                sprite = spriteSheet.getImage(1,1,32,32);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        if(!Game.getGame().isOnPlanet() && !Game.getGame().isBossLvl()) {
            g.drawImage(sprite,x,y,null);
        }
//        //hitbox
//        g.setColor(Color.red);
//        g.drawRect(x,y,32,32);
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

}

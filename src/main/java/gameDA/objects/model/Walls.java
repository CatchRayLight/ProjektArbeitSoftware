package gameDA.objects.model;

import gameDA.config.output.SpriteSheet;
import gameDA.objects.Animation;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Walls extends GameObject {
    private BufferedImage[] sprite = new BufferedImage[4];
    private boolean onPlanet;
    private Animation animation;
    public Walls(int x, int y, ObjectID id, SpriteSheet spriteSheet,boolean onPlanet) {
        super(x, y, id,spriteSheet);
        this.onPlanet = onPlanet;
        sprite[0] = spriteSheet.getImage(1,1,32,32);
        sprite[1] = spriteSheet.getImage(2,1,32,32);
        sprite[2] = spriteSheet.getImage(3,1,32,32);
        sprite[3] = spriteSheet.getImage(4,1,32,32);
        animation = new Animation(50,sprite);
    }

    @Override
    public void update() {
        if(!onPlanet)animation.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        if(!onPlanet) {
            animation.drawAnimation(g,x,y,0);
        }

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

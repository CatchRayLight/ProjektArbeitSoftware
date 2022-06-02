package gameDA.objects.model;

import gameDA.objects.GameObject;
import gameDA.objects.ObjectID;

import java.awt.*;

public class Block extends GameObject {
    public Block(int x, int y, ObjectID id) {
        super(x, y, id);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x,y,32,32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    @Override
    public void collision() {

    }
}
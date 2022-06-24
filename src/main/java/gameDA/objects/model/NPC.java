package gameDA.objects.model;

import gameDA.config.output.SpriteSheet;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectID;

import java.awt.*;

public abstract class NPC extends GameObject {
    public NPC(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
    }
    //bewegung der Objekte
    protected abstract void walk(Graphics g);
    //die fähigkeit bei kollision zu agieren
    protected abstract void speak();

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

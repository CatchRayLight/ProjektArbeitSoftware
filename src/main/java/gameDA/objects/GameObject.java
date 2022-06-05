package gameDA.objects;

import gameDA.config.output.SpriteSheet;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {
    protected int x,y;
    protected float velocityX = 0,velocityY = 0;
    protected ObjectID id;
    protected SpriteSheet spriteSheet;

    public GameObject(int x, int y, ObjectID id, SpriteSheet spriteSheet){
        this.x = x;
        this.y = y;
        this.id = id;
        this.spriteSheet = spriteSheet;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public ObjectID getId(){
        return id;
    }

    public void setId(ObjectID id) {
        this.id = id;
    }

    public abstract void update();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    public abstract Rectangle getTopBounds(int offset);
    public abstract Rectangle getRightBounds(int offset);
    public abstract Rectangle getLeftBounds(int offset);
    public abstract Rectangle getBotBounds(int offset);
}

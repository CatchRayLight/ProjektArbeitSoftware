package gameDA.objects;

import java.awt.*;

public abstract class GameObject {
    protected int x,y;
    protected float velocityX = 0,velocityY = 0;
    protected ObjectID id;

    public GameObject(int x, int y, ObjectID id){
        this.x = x;
        this.y = y;
        this.id = id;
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
    public abstract void collision();

}

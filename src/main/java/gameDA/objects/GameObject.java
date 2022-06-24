package gameDA.objects;

import gameDA.config.output.SpriteSheet;

import java.awt.*;
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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ObjectID getId(){
        return id;
    }

    public void setId(ObjectID id) {
        this.id = id;
    }

    //alle 30 mal die Sekunde wird aktualisiert
    public abstract void update();
    //alle 30 mal die Sekunde wird gerendert
    public abstract void render(Graphics g);
    //bekommt ein Rechteck welches die Hitbox des Spielers darstellt
    public abstract Rectangle getBounds();
    //bekommt ein Rechteck der oberen Kante welches die Hitbox des Spielers darstellt
    public abstract Rectangle getTopBounds(int offset);
    //bekommt ein Rechteck der rechten Kante welches die Hitbox des Spielers darstellt
    public abstract Rectangle getRightBounds(int offset);
    //bekommt ein Rechteck der linken Kante welches die Hitbox des Spielers darstellt
    public abstract Rectangle getLeftBounds(int offset);
    //bekommt ein Rechteck der unteren Kante welches die Hitbox des Spielers darstellt
    public abstract Rectangle getBotBounds(int offset);
}

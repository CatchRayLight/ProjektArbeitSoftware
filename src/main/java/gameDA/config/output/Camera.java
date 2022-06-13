package gameDA.config.output;

import gameDA.Game;
import gameDA.objects.GameObject;



public class Camera {
    private float x,y;

    public Camera(float x,float y){
        this.x = x;
        this.y = y;

    }
    public void update(GameObject gameObject) {

        x += ((gameObject.getX() - x) - Game.SCREEN_WIDTH / 2) * 0.05f;
        y += ((gameObject.getY() - y) - Game.SCREEN_HEIGHT / 2) * 0.05f;

        if(x <= 0) x = 0;
        if(x >= 848) x =848;
        if(y <= 0 ) y = 0;
        if(y >= 1158) y = 1158;

    }
    public float getX() {
        return x;
    }

    public Camera setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Camera setY(float y) {
        this.y = y;
        return this;
    }
}

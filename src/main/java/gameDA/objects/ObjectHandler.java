package gameDA.objects;

import java.awt.*;
import java.util.LinkedList;

public class ObjectHandler {
    public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
    private boolean up = false, down = false, right = false, left = false;

    public void tick(){
        for(int i = 0; i < gameObjects.size(); i++){
            GameObject tempObj = gameObjects.get(i);
            tempObj.tick();
        }
    }
    public void render(Graphics g){
        for(int i = 0; i < gameObjects.size(); i++){
            GameObject tempObj = gameObjects.get(i);
            tempObj.render(g);
        }
    }
    public void addObj(GameObject tempObj){
        gameObjects.add(tempObj);
    }
    public void removeObj(GameObject tempObj){
        gameObjects.remove(tempObj);
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
}

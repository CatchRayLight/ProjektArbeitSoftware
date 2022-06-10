package gameDA.objects;

import java.awt.*;
import java.util.LinkedList;



public class ObjectHandler {

    public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
    private char direction = 'N';
    private boolean up,down,right,left,space;
    private GameObject lastRenderObject;

    public void update(){
        for(int i = 0; i < gameObjects.size(); i++){
            GameObject tempObj = gameObjects.get(i);
            tempObj.update();
        }
    }
    public void render(Graphics g){
        for(int i = 0; i < gameObjects.size(); i++){
            GameObject tempObj = gameObjects.get(i);
            if(!(tempObj.getId() == ObjectID.PLAYER)) {
                tempObj.render(g);
            }else{
                lastRenderObject = gameObjects.get(i);
            }
        }
        lastRenderObject.render(g);
    }
    public void addObj(GameObject tempObj){
        gameObjects.add(tempObj);
    }
    public void removeObj(GameObject tempObj){
        gameObjects.remove(tempObj);
    }

    public char getDirection() {
        return direction;
    }
    public void setDirection(char direction){
        this.direction = direction;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean isLeft() {
        return left;
    }
    public boolean isRight() {
        return right;
    }
    public boolean isDown() {
        return down;
    }
    public boolean isUp() {
        return up;
    }
    public void setSpace(boolean space){
        this.space = space;
    }
    public boolean isSpace(){
        return space;
    }
}

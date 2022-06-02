package gameDA.objects;

import java.awt.*;

public class Physics {
    //collision with rectangle
    public boolean collision(ObjectHandler objectHandler,Rectangle rectangle){
        boolean isCollision = false;
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObj = objectHandler.gameObjects.get(i);
            if(tempObj.getId() == ObjectID.BLOCK){
                if(rectangle.getBounds().intersects(tempObj.getBounds())){
                    isCollision = true;
                }
            }
        }
        return isCollision;
    }
    //player movement
    public float playerMovementX(ObjectHandler objectHandler, float velocityX){
        if(objectHandler.isLeft())  velocityX = -5;
        if(objectHandler.isRight()) velocityX = 5;
        else if(!(objectHandler.isLeft()|| objectHandler.isRight())) velocityX = 0;
        return velocityX;
    }
    public float playerMovementY(ObjectHandler objectHandler, float velocityY){
        if(objectHandler.isUp())  velocityY = -5;
        if(objectHandler.isDown())  velocityY = 5;
        else if(!(objectHandler.isUp()||objectHandler.isDown())) velocityY = 0;
        return velocityY;
    }


}

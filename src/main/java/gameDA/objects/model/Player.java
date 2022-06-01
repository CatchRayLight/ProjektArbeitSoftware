package gameDA.objects.model;


import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;


import java.awt.*;

public class Player extends GameObject {
    ObjectHandler objectHandler;
    public Player(int x, int y, ObjectID id, ObjectHandler objectHandler) {
        super(x, y, id);
        this.objectHandler = objectHandler;
    }

    @Override
    public void tick() {
        x += velocityX;
        y += velocityY;

        collision();
        //player movement
        if(objectHandler.isUp()) velocityY = -5;
        else if(!objectHandler.isDown()) velocityY = 0;

        if(objectHandler.isDown()) velocityY = 5;
        else if(!objectHandler.isUp()) velocityY = 0;

        if(objectHandler.isLeft()) velocityX = -5;
        else if(!objectHandler.isRight()) velocityX = 0;

        if(objectHandler.isRight()) velocityX = 5;
        else if(!objectHandler.isLeft()) velocityX = 0;

    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(x,y,32,32);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
    private void collision(){
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObj = objectHandler.gameObjects.get(i);
            if(tempObj.getId() == ObjectID.BLOCK){
                if(getBounds().intersects(tempObj.getBounds())){
                    x += velocityX * -1;
                    y += velocityY * -1;
                }
            }
        }
    }
}

package gameDA.objects.model;

import gameDA.config.output.SpriteSheet;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;

import java.awt.*;

/**
 * @author Atilla Ipek
 */
public class PlayerBullet extends GameObject {
    private int offsetX;
    private int offsetY;
    private int bulletDirectionY;

    private int bulletDirectionX;
    private ObjectHandler objectHandler;
    public PlayerBullet(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler,int bulletSpeed,char direction) {
        super(x, y, id, spriteSheet);
        this.objectHandler = objectHandler;

        switch (direction){
            case 'U':
                velocityX = 0;
                velocityY = -bulletSpeed;
                offsetX = 16;
                offsetY = 0;
                bulletDirectionX= 2;
                bulletDirectionY = 8;
                break;
            case 'D':
                velocityX = 0;
                velocityY = bulletSpeed;
                offsetY = 32;
                offsetX = 16;
                bulletDirectionX= 2;
                bulletDirectionY = 8;
                break;
            case 'R':
                velocityX = bulletSpeed;
                velocityY = 0;
                offsetX = 32;
                offsetY = 16;
                bulletDirectionX= 8;
                bulletDirectionY = 2;
                break;
            case 'L':
                velocityX = -bulletSpeed;
                velocityY = 0;
                offsetX = 0;
                offsetY = 16;
                bulletDirectionX= 8;
                bulletDirectionY = 2;
                break;
        }

    }

    @Override
    public void update() {
        x += velocityX;
        y += velocityY;
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.BLOCK) {
                if(getBounds().intersects(tempObject.getBounds())){
                    objectHandler.removeObj(this);
//                    objectHandler.removeObj(tempObject);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x+offsetX,y+offsetY,bulletDirectionX,bulletDirectionY);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+offsetX,y+offsetY,bulletDirectionX,bulletDirectionY);
    }

    @Override
    public Rectangle getTopBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getRightBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getLeftBounds(int offset) {
        return null;
    }

    @Override
    public Rectangle getBotBounds(int offset) {
        return null;
    }
}

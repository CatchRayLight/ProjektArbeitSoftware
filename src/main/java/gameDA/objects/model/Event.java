package gameDA.objects.model;

import gameDA.config.output.SpriteSheet;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectHandler;
import gameDA.objects.ObjectID;

import java.awt.*;

public class Event extends GameObject {
    private ObjectHandler objectHandler;
    public Event(int x, int y, ObjectID id, SpriteSheet spriteSheet, ObjectHandler objectHandler) {
        super(x, y, id, spriteSheet);

        this.objectHandler = objectHandler;
    }

    @Override
    public void update() {
        for (int i = 0; i < objectHandler.gameObjects.size() ; i++) {
            GameObject tempObject = objectHandler.gameObjects.get(i);
            if (tempObject.getId() == ObjectID.PLAYER) {
                if(getBounds().intersects(tempObject.getBounds())){
                    System.out.println("Event");
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(x,y,32,32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
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

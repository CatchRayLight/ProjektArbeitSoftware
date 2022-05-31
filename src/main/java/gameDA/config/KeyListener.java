package gameDA.config;

import gameDA.objects.*;

import java.awt.event.*;

public class KeyListener extends KeyAdapter {

    private final ObjectHandler objectHandler;

    public KeyListener(ObjectHandler objectHandler){
        this.objectHandler = objectHandler;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObj = objectHandler.gameObjects.get(i);

            if (tempObj.getId() == ObjectID.PLAYER) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        objectHandler.setLeft(true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        objectHandler.setRight(true);
                        break;
                    case KeyEvent.VK_DOWN:
                        objectHandler.setDown(true);
                        break;
                    case KeyEvent.VK_UP:
                        objectHandler.setUp(true);
                        break;
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
            GameObject tempObj = objectHandler.gameObjects.get(i);
            if (tempObj.getId() == ObjectID.PLAYER) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        objectHandler.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        objectHandler.setRight(false);
                        break;
                    case KeyEvent.VK_DOWN:
                        objectHandler.setDown(false);
                        break;
                    case KeyEvent.VK_UP:
                        objectHandler.setUp(false);
                        break;
                }
            }
        }
    }
}

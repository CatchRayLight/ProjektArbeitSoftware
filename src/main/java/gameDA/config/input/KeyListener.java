package gameDA.config.input;

import gameDA.gui.Gamestate;
import gameDA.gui.menus.MenuHandler;
import gameDA.objects.*;
import gameDA.objects.model.PlayerBullet;


import java.awt.event.*;




public class KeyListener extends KeyAdapter{

    private final ObjectHandler objectHandler;
    private final MenuHandler menuHandler;
    public static boolean frameChange = false;


    private Gamestate gamestate;


    public KeyListener(ObjectHandler objectHandler, MenuHandler menuHandler, Gamestate gamestate) {
        this.objectHandler = objectHandler;
        this.menuHandler = menuHandler;
        this.gamestate = gamestate;
    }


    public void setGamestate(Gamestate gamestate) {
        this.gamestate = gamestate;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamestate.equals(Gamestate.INGAME)) {
            frameChange = true;
            for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
                GameObject tempObj = objectHandler.gameObjects.get(i);
                if (tempObj.getId() == ObjectID.PLAYER) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ESCAPE:
                            menuHandler.openStartMenu();
                            break;
                        case KeyEvent.VK_LEFT:
                            objectHandler.setDirection('L');
                            objectHandler.setLeft(true);
                            break;
                        case KeyEvent.VK_RIGHT:
                            objectHandler.setDirection('R');
                            objectHandler.setRight(true);
                            break;
                        case KeyEvent.VK_DOWN:
                            objectHandler.setDirection('D');
                            objectHandler.setDown(true);
                            break;
                        case KeyEvent.VK_UP:
                            objectHandler.setDirection('U');
                            objectHandler.setUp(true);
                            break;
                        case KeyEvent.VK_SPACE:
                            objectHandler.setSpace(true);
                            break;
                    }
                }
            }
        } else if (gamestate.equals(Gamestate.INMENU)) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    menuHandler.setEnter(true);
                    break;
                case KeyEvent.VK_DOWN:
                    menuHandler.setDown(true);
                    break;
                case KeyEvent.VK_UP:
                    menuHandler.setUp(true);
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gamestate.equals(Gamestate.INGAME)) {
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
                        case KeyEvent.VK_SPACE:
                            objectHandler.setSpace(false);
                            break;
                    }
                }
            }
        }
        if (gamestate.equals(Gamestate.INMENU)) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    menuHandler.setEnter(false);
                    break;
                case KeyEvent.VK_DOWN:
                    menuHandler.setDown(false);
                    break;
                case KeyEvent.VK_UP:
                    menuHandler.setUp(false);
                    break;
            }
        }

    }
}

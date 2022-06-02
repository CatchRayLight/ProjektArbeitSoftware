package gameDA.gui.menus;

import gameDA.Game;

import java.awt.*;

public class MenuHandler {

    private Menu currentMenu;
    private boolean up = false, down = false, enter = false;

    public MenuHandler(Menu startMenu) {
        this.currentMenu = startMenu;

    }

    public void update(Game game){
        currentMenu.update(this, game);
    }
    public void render(Graphics g){
        currentMenu.render(g);
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isEnter() {
        return enter;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }

}

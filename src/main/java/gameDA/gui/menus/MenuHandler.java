package gameDA.gui.menus;

import gameDA.Game;
import gameDA.gui.Gamestate;

import java.awt.*;

public class MenuHandler {

    private Menu currentMenu;
    private Menu startMenu;
    private boolean up = false, down = false, enter = false;
    private Game game;


    public MenuHandler(Menu startMenu, Game game) {
        this.currentMenu = startMenu;
        this.game = game;
        this.startMenu = startMenu;
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

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void openStartMenu() {
        this.currentMenu = startMenu;
        game.updateGamestate(Gamestate.INMENU);
    }
}

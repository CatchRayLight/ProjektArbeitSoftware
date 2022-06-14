package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;

public class DeathMenu extends Menu {


    public DeathMenu(){
        super(new MenuOption[]{new MenuOption(() -> {
            //Return to last safe
        },"Load last save", 100, 100), new MenuOption(() -> {
            Game.getGame().getMenuHandler().openMainMenu();
        }, "Exit to Main-Menu", 100, 200)
        });
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }

    @Override
    public void render(Graphics g) {
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }

    @Override
    public void updateMenu() {

    }
}

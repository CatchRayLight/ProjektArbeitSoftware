package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;

public class ShopMenu extends Menu {


    public ShopMenu() {
        super(new MenuOption[]{new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
        }, "ShopOption1", 100, 100), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
        }, "ShopOption2", 100, 250), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
        }, "ShopOption3", 100, 400), new MenuOption(() -> {
            //Verlasse das Menu
            Game.getGame().getSound().stop();
            Game.getGame().setGamestate(Gamestate.INGAME);
        }, "Exit", 100, 550)
        }, 0);

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

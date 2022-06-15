package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;

public class DeathMenu extends Menu {


    public DeathMenu(){
        super(new MenuOption[]{new MenuOption(() -> {
            //Return to last safe
        },"Load save 1", 400, 400), new MenuOption(() -> {
            Game.getGame().getMenuHandler().openMainMenu();
        }, "Exit to Mainmenu", 400, 500)
        }, 1);
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }

    @Override
    public void render(Graphics g) {

        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
        g.setColor(Color.RED);
        g.setFont(new Font("Courier New",Font.BOLD,60));
        g.drawString("You Died",450,350);
    }

    @Override
    public void updateMenu() {

    }
}

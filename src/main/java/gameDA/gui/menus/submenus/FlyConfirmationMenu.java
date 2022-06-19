package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;
import gameDA.objects.LvLHandler;

import java.awt.*;

public class FlyConfirmationMenu extends Menu {


    public FlyConfirmationMenu() {
        super(new MenuOption[]{});
        setMenuOptions(new MenuOption[]{new MenuOption(() -> {
            LvLHandler lvLHandler = new LvLHandler();
            lvLHandler.nextLvL(Game.getGame().getObjectHandler());
            Game.getGame().setGamestate(Gamestate.INGAME);
        }, "Yes", 275, 350), new MenuOption(() -> {
            Game.getGame().setGamestate(Gamestate.INGAME);
        }, "No", 275, 500)});
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }

    @Override
    public void render(Graphics g) {
        //Render Question: "Do you want to leave?"
        g.setColor(Color.RED);
        g.drawString("Leave  the  planet",300,300);
        g.setColor(Color.BLUE);
        //Render MenuOptions
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }

    @Override
    public void updateMenu() {

    }
}

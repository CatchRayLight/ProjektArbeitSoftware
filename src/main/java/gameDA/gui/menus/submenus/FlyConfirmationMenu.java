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
            //Lade nächstes Level
            LvLHandler lvLHandler = new LvLHandler();
            lvLHandler.nextLvL(Game.getGame().getObjectHandler());
            Game.getGame().setGamestate(Gamestate.INGAME);

        }, "Yes", 275, 350), new MenuOption(() -> {
            //Kehre zurück ins Spiel
            Game.getGame().setGamestate(Gamestate.INGAME);
        }, "No", 275, 500)});
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }
    /**
     * Rendere das FlyConfirmationMenu
     * @param g Grafik mit der gerendert wird
     */
    @Override
    public void render(Graphics g) {
        //Rendere die Frage: "Do you want to leave?"
        g.setColor(Color.RED);
        g.drawString("Verlasse  den Planet",300,300);
        g.setColor(Color.BLUE);
        //Rendere MenuOptionen
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }
    /**
     * Updated das Menu
     */
    @Override
    public void updateMenu() {}
}

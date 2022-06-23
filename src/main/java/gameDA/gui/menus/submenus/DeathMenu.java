package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;
import gameDA.savemanager.SaveKey;

import java.awt.*;
import java.util.ArrayList;

public class DeathMenu extends Menu {


    public DeathMenu(){
        super(new MenuOption[]{new MenuOption(() -> {
            //Return to last safe
            Game.getGame().getSafeManager().load(0);
            Game.getGame().setGamestate(Gamestate.INGAME);
        },"Lade Save 1", 400, 400), new MenuOption(() -> {
            //Lade standard Daten aus den Savekeys und ersetze momentane Daten durch Standard Daten
            ArrayList<String> data = new ArrayList<>();
            for(SaveKey key : SaveKey.values()) {
                data.add(key.identifier + ":" + key.startValue);
            }
            Game.getGame().getSafeManager().load(data);
            //Öffne Mainmenu
            Game.getGame().getMenuHandler().openMainMenu();
        }, "Zurück zum Mainmenu", 400, 500)
        }, 1);
        //Schalte Background aus
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }

    /**
     * Rendere das DeathMenu
     * @param g Grafik mit der gerendert wird
     */
    @Override
    public void render(Graphics g) {
        //rendere Menu Optionen
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
        //Schreibe "You Died"
        g.setColor(Color.RED);
        g.drawString("Du bist",530,350);
        g.drawString("gestorben",620,350);
    }

    /**
     * Updated das Menu
     */
    @Override
    public void updateMenu() {}
}

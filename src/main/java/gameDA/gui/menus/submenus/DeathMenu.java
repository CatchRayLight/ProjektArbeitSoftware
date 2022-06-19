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
        },"Load save 1", 400, 400), new MenuOption(() -> {
            ArrayList<String> data = new ArrayList<>();
            for(SaveKey key : SaveKey.values()) {
                data.add(key.identifier + ":" + key.startValue);
            }
            Game.getGame().getSafeManager().load(data);
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
//        g.setFont(new Font("Courier New",Font.BOLD,60));
        g.drawString("You",530,350);
        g.drawString("Died",620,350);
    }

    @Override
    public void updateMenu() {

    }
}

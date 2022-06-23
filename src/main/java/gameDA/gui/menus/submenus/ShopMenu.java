package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;

public class ShopMenu extends Menu {

    private static boolean tempo = false;
    private static boolean schaden = false;
    private static boolean haeufigkeit = false;
    private static boolean kosten = false;


    public ShopMenu() {
        super(new MenuOption[]{new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!tempo && (Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() >= 20)) {
                tempo = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 20);
                Game.getGame().getObjectHandler().getPlayer().setBulletSpeed(9 + 3);
                Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
            }
        }, "Schusstempo", 100, 100), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!schaden && (Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() >= 20)) {
                schaden = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 20);
                Game.getGame().getObjectHandler().getPlayer().setBulletDmg(20 + 20);
                Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();

            }
        }, "Schaden", 100, 250), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!haeufigkeit && (Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() >= 20)) {
                haeufigkeit = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 20);
                Game.getGame().getObjectHandler().getPlayer().setCooldownBullet(9-2);
                Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();

            }
        }, "Schussrate", 100, 400), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!kosten && (Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() >= 20)) {
                kosten = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 20);
                Game.getGame().getObjectHandler().getPlayer().setBulletCost(10 - 5);
                Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
            }
        }, "Mutionskosten", 100, 550), new MenuOption(() -> {
            //Verlasse das Menu
            Game.getGame().getSound().stop();
            Game.getGame().setGamestate(Gamestate.INGAME);
        }, "Exit", 100, 700)
        }, 0);

    }

    @Override
    public void updateMenu() {
        if(tempo) {
            getMenuOptions()[0].setText("Ausverkauft");
        }
        if(schaden) {
            getMenuOptions()[1].setText("Ausverkauft");
        }
        if(haeufigkeit) {
            getMenuOptions()[2].setText("Ausverkauft");
        }
        if(kosten) {
            getMenuOptions()[3].setText("Ausverkauft");
        }

    }

    public static boolean isTempo() {
        return tempo;
    }

    public static void setTempo(boolean tempo) {
        ShopMenu.tempo = tempo;
    }

    public static boolean isSchaden() {
        return schaden;
    }

    public static void setSchaden(boolean schaden) {
        ShopMenu.schaden = schaden;
    }

    public static boolean isHaeufigkeit() {
        return haeufigkeit;
    }

    public static void setHaeufigkeit(boolean haeufigkeit) {
        ShopMenu.haeufigkeit = haeufigkeit;
    }

    public static boolean isKosten() {
        return kosten;
    }

    public static void setKosten(boolean kosten) {
        ShopMenu.kosten = kosten;
    }


}

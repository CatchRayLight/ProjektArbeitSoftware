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
            if(!tempo) {
                tempo = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 10);
                Game.getGame().getObjectHandler().getPlayer().setBulletSpeed(6 + 6);
            }
        }, "Tempo", 100, 100), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!schaden) {
                schaden = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 10);
                Game.getGame().getObjectHandler().getPlayer().setBulletDmg(20 + 20);
            }
        }, "Schaden", 100, 250), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!haeufigkeit) {
                haeufigkeit = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 10);
                Game.getGame().getObjectHandler().getPlayer().setCooldownBullet(20 - 10);
            }
        }, "Haeufigkeit", 100, 400), new MenuOption(() -> {
            //Speichere dass die Option gekauft wurde und ziehe Geld ab
            if(!kosten) {
                kosten = true;
                Game.getGame().getObjectHandler().getPlayer().setPlayerCoins( Game.getGame().getObjectHandler().getPlayer().getPlayerCoins() - 10);
                Game.getGame().getObjectHandler().getPlayer().setBulletCost(10 - 5);
            }
        }, "Kosten", 100, 550), new MenuOption(() -> {
            //Verlasse das Menu
            Game.getGame().getSound().stop();
            Game.getGame().setGamestate(Gamestate.INGAME);
        }, "Exit", 100, 700)
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

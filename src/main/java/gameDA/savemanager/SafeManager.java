package gameDA.savemanager;

import gameDA.Game;
import gameDA.gui.menus.submenus.ShopMenu;
import gameDA.objects.model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class SafeManager {

    private final Save[] saves; //Die Saves zu denen gespeichert/ von denen geladen wird
    private int currentSaveToUse;//Der Save zu dem gespeichert wird bei Aufruf der Safe-Methode ohne Parameter

    public SafeManager(Save[] saves) {
        this.saves = saves;
        this.currentSaveToUse = 0;
        //Gehe durch alle Saves und initiallisiere sie falls sie neu erstellt wurden
        for(int i = 0; i < 4; i++) {
            if(saves[i].isNewlyCreated()) {
                if (i < 3) {
                    for (SaveKey key : SaveKey.values()) {
                        //Es ist ein normaler Save also belade es mit den Startwerten
                        saves[i].safe(key, key.startValue);
                    }
                }
                if (i == 3) {
                    //Es ist die Config also belade es mit den Optionen
                    saves[i].safeOptions(Game.getGame().getOptions());
                }
            }
        }
    }

    /**
     * Speichere das momentan geladene Spiel
     * @param save Save zu dem gespeichert werden soll
     */
    public void safe(int save) {
        Save currentSave = saves[save];
        //Löschen der vorherigen Daten
        currentSave.delete();

        Player player = Game.getGame().getObjectHandler().getPlayer();
        //Sicher alle relevanten daten
        //Level
        currentSave.safe(SaveKey.LEVEL, String.valueOf(Game.getGame().getLvLInt()));
        //Münzen
        currentSave.safe(SaveKey.MONEY, String.valueOf(Objects.requireNonNull(player).getPlayerCoins()));
        //Leben
        currentSave.safe(SaveKey.HEALTH, String.valueOf(player.getHp()));
        //Munition
        currentSave.safe(SaveKey.AMMUNITION, String.valueOf(player.getAmmo()));
        //Tank
        currentSave.safe(SaveKey.FUEL, String.valueOf(player.getFuel()));
        //Der Laden
        //Kugel Geschwindigkeit
        if(ShopMenu.isTempo()) {
            currentSave.safe(SaveKey.BULLETSPEED, "true");
        } else currentSave.safe(SaveKey.BULLETSPEED, "false");
        //Häufigkeit
        if(ShopMenu.isHaeufigkeit()) {
            currentSave.safe(SaveKey.COOLDOWNBULLET, "true");
        } else currentSave.safe(SaveKey.COOLDOWNBULLET, "false");
        //Munitions Verbrauch
        if(ShopMenu.isKosten()) {
            currentSave.safe(SaveKey.BULLETCOST, "true");
        } else currentSave.safe(SaveKey.BULLETCOST, "false");
        //Schaden
        if(ShopMenu.isSchaden()) {
            currentSave.safe(SaveKey.BULLETDMG, "true");
        } else currentSave.safe(SaveKey.BULLETDMG, "false");
    }

    /**
     * Lädt die Daten aus dem Save
     * @param save der Save
     */
    public void load(int save) {
        //Lade die Daten aus der Save Datei
        Save currentSave = saves[save];
        ArrayList<String> data = currentSave.load();
        load(data);
    }

    /**
     * Lädt die Daten aus
     * @param data diesen gegebenen Informationen
     */
    public void load(ArrayList<String> data) {
        //Spieler kriegen
        Player player = Game.getGame().getObjectHandler().getPlayer();
        //objecthandler leeren
        Game.getGame().getObjectHandler().gameObjects.clear();
        //Alle Daten durchgehen und die momentan geladenen Daten durch sie ersetzten
        for(int i = 0; i < data.size(); i++) {
            //Daten in Identifier und Information aufteilen
            String key;
            String value;
            String[] splitData = data.get(i).split(":");
            key = splitData[0];
            value = splitData[1];
            //Die momentan geladenen Daten durch die gegebenen ersetzten
            switch(key){
                case "bulletDmg":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setSchaden(true);
                        player.setBulletDmg(20 + ShopMenu.getSchadenIncrease());
                    } else {
                        ShopMenu.setSchaden(false);
                        player.setBulletDmg(20);
                    }
                    break;
                case "bulletCost":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setKosten(true);
                        player.setBulletCost(10 + ShopMenu.getKostenIncrease());
                    } else {
                        ShopMenu.setKosten(false);
                        player.setBulletCost(10);
                    }
                    break;
                case "cooldownBullet":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setHaeufigkeit(true);
                        player.setCooldownBullet(9 + ShopMenu.getHaeufigkeitIncrease());
                    } else {
                        ShopMenu.setHaeufigkeit(false);
                        player.setCooldownBullet(9);
                    }
                    break;
                case "bulletSpeed":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setTempo(true);
                        player.setBulletSpeed(9 + ShopMenu.getTempoIncrease());
                    } else {
                        ShopMenu.setTempo(false);
                        player.setBulletSpeed(9);
                    }
                    break;
                case "fuel":
                    player.setFuel(Integer.parseInt(value));
                    break;
                case "Ammunition":
                    player.setAmmo(Integer.parseInt(value));
                    break;
                case "Health":
                    player.setHp(Integer.parseInt(value));
                    break;
                case "Money":
                    player.setPlayerCoins(Integer.parseInt(value));
                    break;
                case "Level":
                    Game.getGame().setLvLInt(Integer.parseInt(value));
                    Game.getGame().setBossLvl(false);
                    Game.getGame().setOnPlanet(true);
                    player.setOnPlanet(true);
                    if(Integer.parseInt(value) == 0) {
                        Game.getGame().levelBuilder(Game.getGame().getLvLHandler().getLvLImage(3));
                    }
                    else {
                        Game.getGame().levelBuilder(Game.getGame().getLvLHandler().getLvLImage(Integer.parseInt(value)));
                    }
                    break;

            }
        }
        //Setze Koordinaten der anderen nötigen Gameobjects neu
        Game.getGame().getCamera().setX(0);
        Game.getGame().getCamera().setY(0);
        player.setX(300);
        player.setY(400);
        //Gib dem Objecthandler den Player mit den neuen Werten und lass die Healthbar anzeigen
        Game.getGame().getObjectHandler().addObj(player);
        Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
    }

    /**
     * Safe zu currentSaveToUse
     */
    public void safe(){
        safe(currentSaveToUse);
    }
    /**
     * Lade von currentSaveToUse
     */
    public void load(){
        load(currentSaveToUse);
    }

    /**
     * Speichere die Optionen
     * @param options Optionen die gespeichert werden
     */
    public void saveOptions(Options options) {
        //Lösche vorherige Daten und speichere neue
        Save currentSave = saves[3];
        currentSave.delete();
        currentSave.safeOptions(options);
    }

    /**
     * Lade gespeicherte Optionen
     * @return gespeicherte Optionen
     */
    public Options loadOptions() {
        return saves[3].loadOptions();
    }

    public void setCurrentSaveToUse(int currentSaveToUse) {
        this.currentSaveToUse = currentSaveToUse;
    }
}

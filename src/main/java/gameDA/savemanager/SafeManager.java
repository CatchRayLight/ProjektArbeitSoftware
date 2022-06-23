package gameDA.savemanager;

import gameDA.Game;
import gameDA.gui.menus.submenus.ShopMenu;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectID;
import gameDA.objects.model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class SafeManager {

    private final Save[] saves;
    private int currentSaveToUse; //The save to save to for the safe methode without parameters

    public SafeManager(Save[] saves) {
        this.saves = saves;
        this.currentSaveToUse = 0;
        for(int i = 0; i < 4; i++) {
            for(SaveKey key : SaveKey.values()) {
            if(saves[i].isNewlyCreated() && i < 3) {
                //it is a Save so put start values in it
                saves[i].safe(key, key.startValue);
            }
            if(saves[i].isNewlyCreated() && i == 3) {
                //it is the config so put start values in it
                saves[i].safeOptions(Game.getGame().getOptions());
            }
            }
        }
    }
    public void safe(int save) {
        Save currentSave = saves[save];
        currentSave.delete();

        //Getting Player:
        Player player = Game.getGame().getObjectHandler().getPlayer();
        //Safe all relevant data
        //Level Player is on
        currentSave.safe(SaveKey.LEVEL, String.valueOf(Game.getGame().getLvLInt()));
        //Saving Money (Note: This throws Nullpointerexception when there is no Player in Objecthandler)
        currentSave.safe(SaveKey.MONEY, String.valueOf(Objects.requireNonNull(player).getPlayerCoins()));
        //Health
        currentSave.safe(SaveKey.HEALTH, String.valueOf(player.getHp()));
        //Ammunition
        currentSave.safe(SaveKey.AMMUNITION, String.valueOf(player.getAmmo()));
        //fuel
        currentSave.safe(SaveKey.FUEL, String.valueOf(player.getFuel()));
        //Shop (whats bought and stuff) (boolean as save, load just inc stats)
        //int bulletSpeed
        if(player.getBulletSpeed() > 6) {
            currentSave.safe(SaveKey.BULLETSPEED, "true");
        } else currentSave.safe(SaveKey.BULLETSPEED, "false");
        //int cooldownBullet
        if(player.getCooldownBullet() > 20) {
            currentSave.safe(SaveKey.COOLDOWNBULLET, "true");
        } else currentSave.safe(SaveKey.COOLDOWNBULLET, "false");
        //int bulletCost
        if(player.getBulletCost() > 10) {
            currentSave.safe(SaveKey.BULLETCOST, "true");
        } else currentSave.safe(SaveKey.BULLETCOST, "false");
        //int bulletDmg
        if(player.getBulletDmg() > 20) {
            currentSave.safe(SaveKey.BULLETDMG, "true");
        } else currentSave.safe(SaveKey.BULLETDMG, "false");
    }
    public void load(int save) {
        //Get the data out of the save file
        Save currentSave = saves[save];
        ArrayList<String> data = currentSave.load();
        //get Player
        Player player = Game.getGame().getObjectHandler().getPlayer();
        //objecthandler clearen
        Game.getGame().getObjectHandler().gameObjects.clear();
        for(int i = 0; i < data.size(); i++) {
            String key;
            String value;
            String[] splitData = data.get(i).split(":");
            key = splitData[0];
            value = splitData[1];
            //Exchange currently used Data for the data in the safe file
            switch(key){
                case "bulletDmg":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setSchaden(true);
                        player.setBulletDmg(20 + 20);
                    } else {
                        ShopMenu.setSchaden(false);
                        player.setBulletDmg(20);
                    }
                    break;
                case "bulletCost":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setKosten(true);
                        player.setBulletCost(10 - 5);
                    } else {
                        ShopMenu.setKosten(false);
                        player.setBulletCost(10);
                    }
                    break;
                case "cooldownBullet":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setHaeufigkeit(true);
                        player.setCooldownBullet(5 - 3);
                    } else {
                        ShopMenu.setHaeufigkeit(false);
                        player.setCooldownBullet(5);
                    }
                    break;
                case "bulletSpeed":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setTempo(true);
                        player.setBulletSpeed(9 + 6);
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
                    //Ask: How to load?
                    //go to last on planet (0,durch 3 teilbar)
                    //immer onplanet 2 also 3 oder 6 (immer 3 oder 6 )
                    Game.getGame().setLvLInt(Integer.parseInt(value));
                    Game.getGame().setBossLvl(false);
                    Game.getGame().setOnPlanet(true);
                    if(Integer.parseInt(value) == 0) {
                        Game.getGame().levelBuilder(Game.getGame().getLvLHandler().getLvLImage(3));
                    }
                    else {
                        Game.getGame().levelBuilder(Game.getGame().getLvLHandler().getLvLImage(Integer.parseInt(value)));
                    }
                    //Set player coordinates
                    break;
            }
        }
        Game.getGame().getCamera().setX(0);
        Game.getGame().getCamera().setY(0);
        player.setOnPlanet(true);
        player.setX(300);
        player.setY(400);
        Game.getGame().getObjectHandler().addObj(player);
        Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
    }

    public void load(ArrayList<String> data) {
        //get Player
        Player player = Game.getGame().getObjectHandler().getPlayer();
        //objecthandler clearen
        Game.getGame().getObjectHandler().gameObjects.clear();
        for(int i = 0; i < data.size(); i++) {
            String key;
            String value;
            String[] splitData = data.get(i).split(":");
            key = splitData[0];
            value = splitData[1];
            //Exchange currently used Data for the data in the safe file
            switch(key){
                case "bulletDmg":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setSchaden(true);
                        player.setBulletDmg(20 + 20);
                    } else {
                        ShopMenu.setSchaden(false);
                        player.setBulletDmg(20);
                    }
                    break;
                case "bulletCost":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setKosten(true);
                        player.setBulletCost(10 - 5);
                    } else {
                        ShopMenu.setKosten(false);
                        player.setBulletCost(10);
                    }
                    break;
                case "cooldownBullet":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setHaeufigkeit(true);
                        player.setCooldownBullet(5 - 2);
                    } else {
                        ShopMenu.setHaeufigkeit(false);
                        player.setCooldownBullet(5);
                    }
                    break;
                case "bulletSpeed":
                    if("true".equalsIgnoreCase(value)) {
                        ShopMenu.setTempo(true);
                        player.setBulletSpeed(9 + 6);
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
                    //Ask: How to load?
                    //go to last on planet (0,durch 3 teilbar)
                    //immer onplanet 2 also 3 oder 6 (immer 3 oder 6 )
                    System.out.println("Level to load: " + value);
                    Game.getGame().setLvLInt(Integer.parseInt(value));
                    Game.getGame().setBossLvl(false);
                    Game.getGame().setOnPlanet(true);
                    if(Integer.parseInt(value) == 0) {
                        Game.getGame().levelBuilder(Game.getGame().getLvLHandler().getLvLImage(3));
                    }
                    else {
                        Game.getGame().levelBuilder(Game.getGame().getLvLHandler().getLvLImage(Integer.parseInt(value)));
                    }
                    //Set player coordinates
                    break;

            }
        }
        Game.getGame().getCamera().setX(0);
        Game.getGame().getCamera().setY(0);
        player.setOnPlanet(true);
        player.setX(300);
        player.setY(400);
        Game.getGame().getObjectHandler().addObj(player);
        Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
        System.out.println("am loading?");
    }
    public void safe(){
        safe(currentSaveToUse);
    }
    public void load(){
        //Get the data out of the save file
        load(currentSaveToUse);
    }
    public void saveOptions(Options options) {
        Save currentSave = saves[3];
        currentSave.delete();
        currentSave.safeOptions(options);
    }

    public Options loadOptions() {
        return saves[3].loadOptions();
    }

    public int getCurrentSaveToUse() {
        return currentSaveToUse;
    }

    public void setCurrentSaveToUse(int currentSaveToUse) {
        this.currentSaveToUse = currentSaveToUse;
    }
}

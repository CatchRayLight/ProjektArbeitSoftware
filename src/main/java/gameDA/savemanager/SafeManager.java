package gameDA.savemanager;

import gameDA.Game;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectID;
import gameDA.objects.model.Player;

import java.util.ArrayList;

public class SafeManager {

    private Save[] saves;
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
        Player player = null;
        for (int i = 0; i < Game.getGame().getObjectHandler().gameObjects.size(); i++) {
            GameObject tempObj = Game.getGame().getObjectHandler().gameObjects.get(i);
            if (tempObj.getId() == ObjectID.PLAYER) {
                player = (Player) tempObj;
                break;
            }
        }
        //Safe all relevant data
        //Level Player is on
        currentSave.safe(SaveKey.LEVEL, String.valueOf(Game.getGame().getLvLInt()));
        //Saving Money (Note: This throws Nullpointerexception when there is no Player in Objecthandler)
        currentSave.safe(SaveKey.MONEY, String.valueOf(player.getPlayerCoins()));
        //Health
        currentSave.safe(SaveKey.MONEY, String.valueOf(player.getPlayerCoins()));
        //Ammunition
        currentSave.safe(SaveKey.MONEY, String.valueOf(player.getPlayerCoins()));
        //fuel
        currentSave.safe(SaveKey.MONEY, String.valueOf(player.getPlayerCoins()));
        //Shop (whats bought and stuff) (boolean as save, load just inc stats)
        currentSave.safe(SaveKey.MONEY, String.valueOf(player.getPlayerCoins()));

    }
    public void load(int save) {
        //Get the data out of the save file
        Save currentSave = saves[save];
        ArrayList<String> data = currentSave.load();
        //get Player
        Player player = Game.getGame().getObjectHandler().getPlayer();
        //objecthandler clearen
        for(int i = 0; i < data.size(); i++) {
            String key;
            String value;
            String[] splitData = data.get(i).split(":");
            key = splitData[0];
            value = splitData[1];
            //Exchange currently used Data for the data in the safe file

            switch(key){
                case "Money":
                    player.setPlayerCoins(Integer.parseInt(key));
                    break;
                case "Level":
                    //Ask: How to load?
                    //go to last on planet (0,durch 3 teilbar)
                    //immer onplanet 2 also 3 oder 6 (immer 3 oder 6 )
                    if(Game.getGame().getLvLInt() == 0)Game.getGame().levelBuilder(Game.getGame().getLvLHandler().setLvL(3));
                    else Game.getGame().levelBuilder(Game.getGame().getLvLHandler().setLvL(Game.getGame().getLvLInt()));
                    break;

            }
        }
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

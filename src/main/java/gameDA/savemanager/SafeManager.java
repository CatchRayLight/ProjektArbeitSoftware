package gameDA.savemanager;

import gameDA.Game;

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

        //Safe all relevant data
        currentSave.safe(SaveKey.PLAYERX, Integer.toString(5));
        currentSave.safe(SaveKey.PLAYERY, Integer.toString(20));
    }
    public void load(int save) {
        //Get the data out of the save file
        Save currentSave = saves[save];
        ArrayList<String> data = currentSave.load();
        for(int i = 0; i < data.size(); i++) {
            String key;
            String value;
            String[] splitData = data.get(i).split(":");
            key = splitData[0];
            value = splitData[1];
            //Exchange currently used Data for the data in the safe file
            switch(key){
                case "PlayerX":
                    break;
                case "PlayerY":
                    break;
            }
        }
    }
    public void safe(){
        Save currentSave = saves[currentSaveToUse];
        currentSave.delete();

        //Safe all relevant data (To be implemented)
        currentSave.safe(SaveKey.PLAYERX, Integer.toString(5));
        currentSave.safe(SaveKey.PLAYERY, Integer.toString(20));
    }

    public void load(){
        //Get the data out of the save file
        Save currentSave = saves[currentSaveToUse];
        ArrayList<String> data = currentSave.load();
        for(int i = 0; i < data.size(); i++) {
            String key;
            String value;
            String[] splitData = data.get(i).split(":");
            key = splitData[0];
            value = splitData[1];
            //Exchange currently used Data for the data in the safe file (To be implemented)
            switch(key){
                case "PlayerX":
                    break;
                case "PlayerY":
                    break;
            }
        }
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

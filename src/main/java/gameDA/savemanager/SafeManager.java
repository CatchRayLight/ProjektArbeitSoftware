package gameDA.savemanager;

import java.util.ArrayList;

public class SafeManager {

    private Save[] saves;
    private int currentSave = 0;

    public SafeManager(Save[] saves) {
        this.saves = saves;
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
            System.out.println("Test");
            switch(key){
                case "PlayerX":
                    System.out.println("X = " + value);
                    break;
                case "PlayerY":
                    System.out.println("Y = " + value);
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


}

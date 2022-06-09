package gameDA.savemanager;


import gameDA.Game;

import java.io.*;
import java.util.ArrayList;

public class Save {

    private String location;

    public Save(String location) {
        this.location = location;
    }

    public void safe(SaveKey saveKey, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(location));
            writer.write(value);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> load() {
        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(location));
            String readLine;
            while((readLine=reader.readLine()) != null ) {
                output.add(readLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}

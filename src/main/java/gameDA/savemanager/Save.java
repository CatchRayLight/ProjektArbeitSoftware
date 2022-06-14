package gameDA.savemanager;


import gameDA.Game;

import java.io.*;
import java.util.ArrayList;

public class Save {

    private String location;

    public Save(String location) {
        this.location = location;
        File file = new File(location);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void safe(SaveKey saveKey, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(location, true));
            writer.write(saveKey.identifier + ":" + value);
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

    public void safeOptions(Options options) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(location));
            writer.write("" + options.isMusic());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Options loadOptions() {
        boolean music = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(location));
            String readLine = reader.readLine(); //Currently music
            music = Boolean.getBoolean(readLine);
            reader.close();
        } catch (FileNotFoundException e) {
            return new Options(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Options(music);
    }

    public void delete() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(location));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

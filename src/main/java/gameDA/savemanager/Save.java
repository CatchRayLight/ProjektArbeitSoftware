package gameDA.savemanager;



import java.io.*;
import java.util.ArrayList;

public class Save {

    private String location;
    private final String path;
    private boolean newlyCreated = false;

    public Save(String location, String name) {
        this.location = location;
        this.path = location + "/" + name;
        File file = new File(location);
        if(!file.exists()) {
            file.mkdir();
        }
        file = new File(location + "/" + name);
        try {
            newlyCreated = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void safe(SaveKey saveKey, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
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
            BufferedReader reader = new BufferedReader(new FileReader(path));
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("" + options.isMusic());
            writer.newLine();
            writer.write("" + options.isAutoSave());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Options loadOptions() {
        boolean music = false;
        boolean autoSave = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String readLine = reader.readLine(); //Currently music
            music = "true".equalsIgnoreCase(readLine);
            readLine = reader.readLine(); //Currently autoSave
            autoSave = "true".equalsIgnoreCase(readLine);

            reader.close();
        } catch (FileNotFoundException e) {
            return new Options(true, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Options(music, autoSave);
    }

    public void delete() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }
}

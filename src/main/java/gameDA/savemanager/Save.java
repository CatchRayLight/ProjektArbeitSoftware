package gameDA.savemanager;



import java.io.*;
import java.util.ArrayList;

public class Save {

    private final String path;//Pfad der Save Datei
    private boolean newlyCreated = false;//Ob die Datei neu generiert wurde

    public Save(String location, String name) {
        //Setze die Attribute und erstelle die Save Datei falls sie noch nicht vorhanden
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

    /**
     * Speichert einen Wert zu der Save Datei mit dem Identifier des SaveKeys
     * Format des Speicherns: "Identifier:Value"
     * @param saveKey benutzter SaveKey
     * @param value Wert zu speichern
     */
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


    /**
     * Lädt die in der Save Datei gespeicherten Daten
     * @return eine Liste aller Zeilen
     */
    public ArrayList<String> load() {
        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String readLine;
            //Speichere alle Zeilen zu der output Liste
            while((readLine=reader.readLine()) != null ) {
                output.add(readLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Speichert die Optionen mit festgesetzter Reihenfolge
     * @param options Optionen zum speichern
     */
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

    /**
     * Lade die optionen in der Save Datei
     * @return gespeicherte Options
     */
    public Options loadOptions() {
        boolean music = false;
        boolean autoSave = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String readLine = reader.readLine(); //Momentan musik, da Reihenfolge des Speicherns
            music = "true".equalsIgnoreCase(readLine);
            readLine = reader.readLine(); //Momentan autoSave, da Reihenfolge des Speicherns
            autoSave = "true".equalsIgnoreCase(readLine);
            reader.close();
        } catch (FileNotFoundException e) {
            return new Options(true, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Options(music, autoSave);
    }

    /**
     * Löscht jeglichen Inhalt der Save Datei
     */
    public void delete() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(""); //Überschreibt alle in der Datei enthaltenen Zeichen
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }
}

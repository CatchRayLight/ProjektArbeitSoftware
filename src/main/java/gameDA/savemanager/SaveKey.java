package gameDA.savemanager;

/**
 * Die SaveKey enthalten einen Identifier und ein Startwert.
 * Der Startwert wird bei der Erstellung von neuen Save Dateien benutzt
 * Der Identifier wird in der Save Datei zur Identifizierung der gespeicherten
 * Information benutzt
 */
public enum SaveKey {
    LEVEL("Level", "0"),
    HEALTH("Health", "100"),
    AMMUNITION("Ammunition", "100"),
    FUEL("fuel", "15600"),
    BULLETSPEED("bulletSpeed", "false"),
    COOLDOWNBULLET("cooldownBullet", "false"),
    BULLETCOST("bulletCost","false"),
    BULLETDMG("bulletDmg", "false"),
    MONEY("Money", "0");

    //(To be implemented)

    public final String identifier;
    public final String startValue;
    SaveKey(String identifier, String startValue) {
        this.identifier = identifier;
        this.startValue = startValue;
    }
}

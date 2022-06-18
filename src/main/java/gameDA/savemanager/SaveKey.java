package gameDA.savemanager;

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

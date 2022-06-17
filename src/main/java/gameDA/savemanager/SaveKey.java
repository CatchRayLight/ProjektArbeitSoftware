package gameDA.savemanager;

public enum SaveKey {
    PLAYERX("PlayerX", "100"),
    PLAYERY("PlayerY", "100"),
    LEVEL("Level", "0");
    //(To be implemented)

    public final String identifier;
    public final String startValue;
    SaveKey(String identifier, String startValue) {
        this.identifier = identifier;
        this.startValue = startValue;
    }
}

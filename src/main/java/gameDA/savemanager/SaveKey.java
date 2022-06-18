package gameDA.savemanager;

public enum SaveKey {
    LEVEL("Level", "0"),
    MONEY("Money", "0");

    //(To be implemented)

    public final String identifier;
    public final String startValue;
    SaveKey(String identifier, String startValue) {
        this.identifier = identifier;
        this.startValue = startValue;
    }
}

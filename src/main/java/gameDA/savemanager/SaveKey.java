package gameDA.savemanager;

public enum SaveKey {
    PLAYERX("PlayerX"),
    PLAYERY("PlayerY");

    public final String identifier;
    SaveKey(String identifier) {
        this.identifier = identifier;
    }
}

package gameDA.saveManager;

public class SafeManager {

    private Save[] saves;

    public SafeManager(Save[] saves) {
        this.saves = saves;
    }
    public void safe(int save) {
        String toSave = "";
        saves[save].safe(toSave);
    }
    public void load(int save) {
        saves[save].load();
    }
}

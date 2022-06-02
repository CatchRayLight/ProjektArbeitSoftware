package gameDA.gui.menus;

public class MenuOption {
    private final Runnable runnable;
    public MenuOption(Runnable runnable) {
        this.runnable = runnable;
    }
    public void execute() {
        runnable.run();
    }
}

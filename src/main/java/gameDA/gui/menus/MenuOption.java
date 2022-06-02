package gameDA.gui.menus;

import gameDA.Game;

public class MenuOption {
    private final Runnable runnable;
    public MenuOption(Runnable runnable) {
        this.runnable = runnable;
    }
    public void execute(Game game) {
        runnable.run();
    }
}

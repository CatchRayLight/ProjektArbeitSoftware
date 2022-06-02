package gameDA.gui.menus;

import gameDA.gui.Game;
import gameDA.objects.ObjectHandler;

public abstract class Menu {
    private int currentOption;
    public final int maxOption;
    public final int minOption;
    private MenuOption[] menuOptions;

    public Menu(MenuOption[] menuOptions) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length - 1;
        this.minOption = 0;
        this.menuOptions = menuOptions;
    }
    private void nextOption() {
        currentOption++;
        if(currentOption > maxOption) {
            currentOption = minOption;
        }
        render();
    }
    private void previousOption() {
        currentOption--;
        if(currentOption < minOption) {
            currentOption = minOption;
        }
        render();
    }

    public void update(MenuHandler menuHandler, Game game) {
        if(menuHandler.isUp()) {
            previousOption();
        }
        if(menuHandler.isDown()){
            nextOption();
        }
        if(menuHandler.isEnter()) {
            select(game);
        }
    }

    /**
     * Runs the code associated with the MenuOption currently selected through
     * the execute Method of MenuOption
     */
    public void select(Game game) {
        menuOptions[currentOption].execute(game);
    }

    /**
     * Renders the Menu
     */
    public abstract void render();
}

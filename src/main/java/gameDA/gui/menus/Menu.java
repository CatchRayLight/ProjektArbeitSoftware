package gameDA.gui.menus;

public abstract class Menu {
    private int currentOption;
    public final int maxOption;
    public final int minOption;
    private MenuOption[] menuOptions;

    public Menu(MenuOption[] menuOptions) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length;
        this.minOption = 0;
        this.menuOptions = menuOptions;
    }
    public void nextOption() {
        currentOption++;
        if(currentOption > maxOption) {
            currentOption = minOption;
        }
        render();
    }
    public void previousOption() {
        currentOption--;
        if(currentOption < minOption) {
            currentOption = minOption;
        }
        render();
    }

    /**
     * Runs the code associated with the MenuOption currently selected through
     * the execute Method of MenuOption
     */
    public void select() {
        menuOptions[currentOption].execute();
    }

    /**
     * Renders the Menu
     */
    public abstract void render();
}

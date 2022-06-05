package gameDA.gui.menus;

import gameDA.Game;

import java.awt.*;

public abstract class Menu {
    private int currentOption;
    private int changeCurrentoptionCooldown = 0;
    private int enterCooldown = 0;
    public int maxOption;
    public final int minOption;
    private MenuOption[] menuOptions;

    public Menu(MenuOption[] menuOptions) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length - 1;
        this.minOption = 0;
        this.menuOptions = menuOptions;
    }
    private void nextOption() {
        menuOptions[currentOption].setSelected(false);
        currentOption++;
        if(currentOption > maxOption) {
            currentOption = minOption;
        }
    }
    private void previousOption() {
        menuOptions[currentOption].setSelected(false);
        currentOption--;
        if(currentOption < minOption) {
            currentOption = maxOption;
        }
    }

    public void update(MenuHandler menuHandler, Game game) {
        if(changeCurrentoptionCooldown <= 0) {
            changeCurrentoptionCooldown = 20;
            if (menuHandler.isUp()) {
                previousOption();
            }
            if (menuHandler.isDown()) {
                nextOption();
            }
            menuOptions[currentOption].setSelected(true);
        }
        changeCurrentoptionCooldown--;
        //maybe change if performance
        if(enterCooldown <= 0) {
            enterCooldown = 100;
            if (menuHandler.isEnter()) {
                select(game);
            }
        }
        enterCooldown--;
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
    public abstract void render(Graphics g);

    public int getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(int currentOption) {
        this.currentOption = currentOption;
    }

    public int getMaxOption() {
        return maxOption;
    }

    public int getMinOption() {
        return minOption;
    }

    public MenuOption[] getMenuOptions() {
        return menuOptions;
    }

    public void setMenuOptions(MenuOption[] menuOptions) {
        maxOption = menuOptions.length - 1;
        this.menuOptions = menuOptions;
    }
}

package gameDA.gui.menus;

import gameDA.Game;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Menu {
    private int currentOption;
    private int changeCurrentoptionCooldown = 20;
    private int enterCooldown = 20;
    public int maxOption;
    private int backgroundMusic;
    public final int minOption;
    private MenuOption[] menuOptions;


    public Menu(MenuOption[] menuOptions) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length - 1;
        this.minOption = 0;
        this.menuOptions = menuOptions;
        this.backgroundMusic = -1;
    }

    public Menu(MenuOption[] menuOptions, int backgroundMusic) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length - 1;
        this.minOption = 0;
        this.menuOptions = menuOptions;
        this.backgroundMusic = backgroundMusic;
    }

    public void startMusic() {
        if(backgroundMusic >= 0 && Game.getGame().getSound() != null) {
            Game.getGame().getSound().setClip(backgroundMusic);
            Game.getGame().getSound().play();
            Game.getGame().getSound().loop();
        }
    }

    public int getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(int backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
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

    public void update(MenuHandler menuHandler) {
        if(changeCurrentoptionCooldown <= 0) {
            if (menuHandler.isUp()) {
                changeCurrentoptionCooldown = 20;
                previousOption();
            }
            if (menuHandler.isDown()) {
                changeCurrentoptionCooldown = 20;
                nextOption();
            }
            menuOptions[currentOption].setSelected(true);
        } else changeCurrentoptionCooldown--;
        if(enterCooldown <= 0) {
            if (menuHandler.isEnter()) {
                menuHandler.setEnter(false);
                enterCooldown = 20;
                select();
            }
        } else enterCooldown--;
        updateMenu();
    }

    public int getEnterCooldown() {
        return enterCooldown;
    }

    public void setEnterCooldown(int enterCooldown) {
        this.enterCooldown = enterCooldown;
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
    public abstract void render(Graphics g);

    public abstract void updateMenu();
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

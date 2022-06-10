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
    public final int minOption;
    private MenuOption[] menuOptions;
    private Font customFont;

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
                enterCooldown = 20;
                select(game);
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
    public void select(Game game) {
        menuOptions[currentOption].execute(game);
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
    protected Font loadFont(){
        try {
            //create the font to use. Specify the size!
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/ARCADECLASSIC.TTF")).deriveFont(12f);
            return customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}

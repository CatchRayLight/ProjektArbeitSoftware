package gameDA.gui.menus;

import gameDA.Game;

import java.awt.*;

public abstract class Menu {
    //Die Stelle der momentan ausgewählten MenuOption
    private int currentOption;
    //Die Wartezeit bis zum nächsten möglichen Wechsel der momentan ausgewählten MenuOption
    private int changeCurrentoptionCooldown = 20;
    //Die Wartezeit bis zur nächsten möglichen Auswahl der momentan ausgewählten MenuOption
    private int enterCooldown = 20;
    //Die Stelle der letzten MenuOption
    private int maxOption;
    //Die Stelle an der die Hintergrundmusik in Sound zu finden ist
    private int backgroundMusic;
    //Alle MenuOptions des Menus
    private MenuOption[] menuOptions;


    public Menu(MenuOption[] menuOptions) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length - 1;
        this.menuOptions = menuOptions;
        this.backgroundMusic = -1;
    }

    public Menu(MenuOption[] menuOptions, int backgroundMusic) {
        this.currentOption = 0;
        this.maxOption = menuOptions.length - 1;
        this.menuOptions = menuOptions;
        this.backgroundMusic = backgroundMusic;
    }

    /**
     * Startet die Musik des Menus und spielt diese dauerhaft
     */
    public void startMusic() {
        if(backgroundMusic >= 0 && (Game.getGame().getSound() != null )&& Game.getGame().getOptions().isMusic()) {
            //Falls eine Hintergrundmusik für das Menu gegeben ist und
            //Musik in den Einstellungen nicht ausgestellt ist, sowie
            //Sound schon initialisiert wurde

            //Setze den Clip auf die Hintergrundmusik
            Game.getGame().getSound().setClip(backgroundMusic);
            //Spiele den Clip ab
            Game.getGame().getSound().play();
            //Mache den Clip in Dauerschleife
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
            currentOption = 0;
        }
    }
    private void previousOption() {
        menuOptions[currentOption].setSelected(false);
        currentOption--;
        if(currentOption < 0) {
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
        menuOptions[this.currentOption].setSelected(false);
        this.currentOption = currentOption;
    }

    public int getMaxOption() {
        return maxOption;
    }


    public MenuOption[] getMenuOptions() {
        return menuOptions;
    }

    public void setMenuOptions(MenuOption[] menuOptions) {
        maxOption = menuOptions.length - 1;
        this.menuOptions = menuOptions;
    }
}

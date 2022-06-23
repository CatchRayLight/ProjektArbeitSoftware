package gameDA.gui.menus;

import gameDA.Game;

import java.awt.*;

public abstract class Menu {
    //Die Stelle der momentan ausgewählten MenuOption
    private int currentOption;
    //Die Wartezeit bis zum nächsten möglichen Wechsel der momentan ausgewählten MenuOption
    private int changeCurrentoptionCooldown = 10;
    //Die Wartezeit bis zur nächsten möglichen Auswahl der momentan ausgewählten MenuOption
    private int enterCooldown = 10;
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

    /**
     * Setzt
     */
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
                changeCurrentoptionCooldown = 10;
                previousOption();
            }
            if (menuHandler.isDown()) {
                changeCurrentoptionCooldown = 10;
                nextOption();
            }
            menuOptions[currentOption].setSelected(true);
        } else changeCurrentoptionCooldown--;
        if(enterCooldown <= 0) {
            if (menuHandler.isEnter()) {
                menuHandler.setEnter(false);
                enterCooldown = 10;
                select();
            }
        } else enterCooldown--;
        updateMenu();
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
    public void render(Graphics g) {
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }

    /**
     * Updated das Menu an sich
     */
    public abstract void updateMenu();

    public void setCurrentOption(int currentOption) {
        menuOptions[this.currentOption].setSelected(false);
        this.currentOption = currentOption;
    }

    public MenuOption[] getMenuOptions() {
        return menuOptions;
    }

    public void setMenuOptions(MenuOption[] menuOptions) {
        maxOption = menuOptions.length - 1;
        this.menuOptions = menuOptions;
    }

    public int getBackgroundMusic() {
        return backgroundMusic;
    }
}

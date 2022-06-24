package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DialogueMenu extends Menu {

    //Position des Menus sowie Linienbreite
    private final int positionX = 0;
    private final int positionY = 690;
    private final int linewidth = 30;

    private String[][] dialogueText; //Dialog Text
    private int currentDialoguePosition = 0; //Momentane Position im Dialog

    //Ressourcen laden
    private final BufferedImageLoader loader = new BufferedImageLoader();
    private final BufferedImage backgroundNext = loader.loadImage("/menu/DialogueMenuNext.png");
    private final BufferedImage backgroundPrev = loader.loadImage("/menu/DialogueMenuPrev.png");

    /**
     * Dialog Menu ohne Funktionalität die nach dem Dialog ausgeführt wird
     * @param dialogueText Dialog
     */
    public DialogueMenu(String[][] dialogueText) {
        super(new MenuOption[]{});
        setMenuOptions(new MenuOption[]{new MenuOption(() -> {
            //Gehe zur nächsten position im Dialog, gehe zurück INGAME falls die letzte bereits erreicht wurde
            if(currentDialoguePosition + 1 >= dialogueText.length) {
                Game.getGame().setGamestate(Gamestate.INGAME);
            } else currentDialoguePosition++;

        }, "Next", 100, 100), new MenuOption(() -> {
            //Gehe zur vorherigen Position im Dialog falls möglich
            if(currentDialoguePosition - 1 < 0) {
            } else currentDialoguePosition--;

        }, "Previous", 100, 250)});
        this.dialogueText = dialogueText;
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }

    /**
     * Dialog Menu mit Funktionalität die nach dem Dialog ausgeführt wird
     * @param dialogueText Dialog
     */
    public DialogueMenu(String[][] dialogueText, Runnable runnable) {
        super(new MenuOption[]{});
        setMenuOptions(new MenuOption[]{new MenuOption(() -> {
            //Gehe zur nächsten position im Dialog, führe Funktionalität aus nach dem Dialog
            if(currentDialoguePosition + 1 >= dialogueText.length) {
                runnable.run();
            } else currentDialoguePosition++;

        }, "Nächste", 100, 100), new MenuOption(() -> {
            //Gehe zur vorherigen Position im Dialog falls möglich
            if(currentDialoguePosition - 1 < 0) {
            } else currentDialoguePosition--;

        }, "Vorherige", 100, 250)});
        this.dialogueText = dialogueText;
        //Schalte Hintergrund aus
        Game.getGame().getMenuHandler().setLoadBackground(false);
    }

    /**
     * rendere das Dialogue Menu
     * @param g
     */
    @Override
    public void render(Graphics g) {
        //render Gameobjects
        Game.getGame().getObjectHandler().render(g);

        //Rendere die dialog box und den Text
        if(getMenuOptions()[0].isSelected()) {
            g.drawImage(backgroundNext,-7,-39,null);
        } else {
            g.drawImage(backgroundPrev,-7,-39,null);
        }

        g.setColor(Color.BLUE);
        g.setFont(new Font("Gloucester MT Extra Condensed",Font.BOLD,32));
        for(int i = 0; i <dialogueText[currentDialoguePosition].length; i++) {
            g.drawString(dialogueText[currentDialoguePosition][i],positionX+30,positionY+70 + linewidth * i);
        }
    }

    /**
     * Updated das Menu
     */
    @Override
    public void updateMenu() {}
}

package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DialogueMenu extends Menu {


    private final int positionX = 0;
    private final int positionY = 690;
    private final int linewidth = 20;
    private String[][] dialogueText;
    private int currentDialoguePosition = 0;
    private final BufferedImageLoader loader = new BufferedImageLoader();
    private final BufferedImage backgroundNext = loader.loadImage("/DialogueMenuNext.png");
    private final BufferedImage backgroundPrev = loader.loadImage("/DialogueMenuPrev.png");

    public DialogueMenu(String[][] dialogueText, Game game) {
        super(new MenuOption[]{});
        setMenuOptions(new MenuOption[]{new MenuOption(() -> {
            if(currentDialoguePosition + 1 >= dialogueText.length) {
                game.updateGamestate(Gamestate.INGAME);
            } else currentDialoguePosition++;
        }, "Next", 100, 100), new MenuOption(() -> {
            if(currentDialoguePosition - 1 < 0) {
            } else currentDialoguePosition--;
        }, "Previous", 100, 250)});
        this.dialogueText = dialogueText;
    }


    @Override
    public void render(Graphics g) {
        //render Gameobjects
        //Render dialogue box
        if(getMenuOptions()[0].isSelected() && getMenuOptions()[0].getText().equalsIgnoreCase("Next") ) {
            g.drawImage(backgroundNext,-7,-39,null);
        } else {
            g.drawImage(backgroundPrev,-7,-39,null);
        }
        //Render dialogue Text
        g.setColor(Color.BLUE);
        for(int i = 0; i <dialogueText[currentDialoguePosition].length; i++) {
            g.drawString(dialogueText[currentDialoguePosition][i],positionX+30,positionY+50 + linewidth * i);
        }
        //Maybe later: Render Characters in the scene, Names

    }
    @Override
    public void updateMenu() {

    }
}

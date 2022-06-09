package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;

public class DialogueMenu extends Menu {


    private final int positionX = 200;
    private final int positionY = 200;
    private String[] dialogueText;
    private int currentDialoguePosition = 0;

    public DialogueMenu(String[] dialogueText, Game game) {
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
        //Render previous and Next buttons
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
        //Render dialogue Box (WIP)
        g.setColor(Color.BLUE);
        g.drawString(dialogueText[currentDialoguePosition],positionX+30,positionY+50);
        g.drawRect(positionX,positionY,400,100);

        //Maybe later: Render Characters in the scene, Names

    }
}

package gameDA.gui.menus;

import gameDA.config.output.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuOption {

    private final Runnable runnable; //Funktionalität der Option
    private boolean selected = false; //Ob die Option gerade selektiert ist
    private String text; //Aufschrieb
    //Position
    private final int positionX;
    private final int positionY;
    //Ressourcen werden geladen
    private final BufferedImageLoader loader = new BufferedImageLoader();
    private final BufferedImage selectedButton = loader.loadImage("/menu/selectedMainMenuButton.png");
    private final BufferedImage unselectedButton = loader.loadImage("/menu/MainMenuButton.png");

    public MenuOption(Runnable runnable, String text, int positionX, int positionY) {
        this.runnable = runnable;
        this.text = text;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Rendere die MenuOption
     * @param g Graphik mit der gerendert wird
     */
    public void render(Graphics g) {
        //Rendere den Text und je nach dem ob es selektiert ist einen unterschiedlichen Knopf
        if(selected) {
            g.drawImage(selectedButton, positionX,positionY,400,96,null);
            //g.drawRect(positionX,positionY,400,96);
        } else {
            g.drawImage(unselectedButton, positionX,positionY,400,96,null);
            //g.drawRect(positionX,positionY,400,96);
        }
        g.setColor(Color.BLUE);
        g.drawString(text,positionX+30,positionY+70);
    }

    /**
     * Führe die Funktionalität des Knopfes aus
     */
    public void execute() {
        runnable.run();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return selected;
    }
}

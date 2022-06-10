package gameDA.gui.menus;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class MenuOption {
    private final Runnable runnable;
    private boolean selected = false;
    private final String text;
    private int positionX;
    private int positionY;
    private BufferedImageLoader loader = new BufferedImageLoader();
    private BufferedImage selectedButton = loader.loadImage("/selectedMainMenuButton.png");
    private BufferedImage unselectedButton = loader.loadImage("/MainMenuButton.png");

    public MenuOption(Runnable runnable, String text, int positionX, int positionY) {
        this.runnable = runnable;
        this.text = text;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void render(Graphics g) {
        if(selected) {
            g.drawImage(selectedButton, positionX,positionY,400,96,null);
            g.setColor(Color.BLUE);
            g.drawString(text,positionX+30,positionY+70);
            //g.drawRect(positionX,positionY,400,96);
        } else {
            g.drawImage(unselectedButton, positionX,positionY,400,96,null);

            g.setColor(Color.BLUE);
            g.drawString(text,positionX+30,positionY+70);
            //g.drawRect(positionX,positionY,400,96);
        }
    }
    public void execute(Game game) {
        runnable.run();
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return selected;
    }
}

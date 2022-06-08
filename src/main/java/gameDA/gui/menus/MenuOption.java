package gameDA.gui.menus;

import gameDA.Game;
import java.awt.*;

public class MenuOption {
    private final Runnable runnable;
    private boolean selected = false;
    private final String text;
    private int positionX;
    private int positionY;
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
            g.setColor(Color.red);
            g.drawString(text,positionX+30,positionY+50);
            g.drawRect(positionX,positionY,400,100);
        } else {
            g.setColor(Color.BLUE);
            g.drawString(text,positionX+30,positionY+50);
            g.drawRect(positionX,positionY,400,100);
        }
    }
    public void execute(Game game) {
        runnable.run();
    }
}

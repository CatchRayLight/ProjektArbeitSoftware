package gameDA.gui.menus;

import java.awt.*;

public class StartMenu extends Menu {

    public StartMenu(MenuOption[] menuOptions) {
        super(menuOptions);
    }

    @Override
    public void render(Graphics g) {
        //test
        g.setColor(Color.red);
        g.drawString("Play",100,100);
        g.drawRect(100,100,200,200);
    }


}

package gameDA.gui.menus;

import java.awt.*;

public class SaveMenu extends Menu{
    public SaveMenu(MenuOption[] menuOptions) {
        super(menuOptions);
    }

    @Override
    public void render(Graphics g) {
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }
}

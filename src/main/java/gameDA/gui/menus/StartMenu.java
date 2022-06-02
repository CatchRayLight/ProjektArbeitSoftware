package gameDA.gui.menus;

import java.awt.*;

public class StartMenu extends Menu {

    public StartMenu(MenuOption[] menuOptions) {
        super(menuOptions);
    }

    @Override
    public void render(Graphics g) {
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            if(i == getCurrentOption()){
                op.setSelected(true);
            } else op.setSelected(false);
            op.render(g);
        }
    }


}

package gameDA.gui.menus.submenus;

import gameDA.config.output.BufferedImageLoader;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;
import gameDA.objects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OptionsMenu extends Menu {
    public OptionsMenu(MenuOption[] menuOptions) {
        super(menuOptions, 0);
    }

    @Override
    public void render(Graphics g) {
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }
    @Override
    public void updateMenu() {
    }
}

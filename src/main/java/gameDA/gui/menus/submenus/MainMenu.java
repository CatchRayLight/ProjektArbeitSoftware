package gameDA.gui.menus.submenus;

import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import java.awt.*;

public class StartMenu extends Menu {
    private BufferedImageLoader loader = new BufferedImageLoader();
    private BufferedImage backgroud1 = loader.loadImage("/MainMenueBackground.png");
    public StartMenu(MenuOption[] menuOptions){
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

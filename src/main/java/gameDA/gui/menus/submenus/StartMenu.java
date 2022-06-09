package gameDA.gui.menus.submenus;

import gameDA.config.output.BufferedImageLoader;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartMenu extends Menu {
    private BufferedImageLoader loader = new BufferedImageLoader();
    private BufferedImage backgroud1 = loader.loadImage("/MainMenueBackground.png");
    public StartMenu(MenuOption[] menuOptions){
        super(menuOptions);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(backgroud1,0,0,null);
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);


            //
        }
    }


}

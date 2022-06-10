package gameDA.gui.menus.submenus;

import gameDA.config.output.BufferedImageLoader;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;
import gameDA.objects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SaveMenu extends Menu {

    private final BufferedImageLoader loader = new BufferedImageLoader();
    private final BufferedImage background1 = loader.loadImage("/MainMenueBackground.png");

    private final Animation animation;

    public SaveMenu(MenuOption[] menuOptions) {
        super(menuOptions);
        BufferedImage backgroundGuy2 = loader.loadImage("/BackgroundGuy2.png");
        BufferedImage backgroundGuy1 = loader.loadImage("/BackgroundGuy1.png");
        BufferedImage backgroundGuy = loader.loadImage("/BackgroundGuy.png");
        BufferedImage[] backgroundGuyS = {backgroundGuy, backgroundGuy1, backgroundGuy2};
        animation = new Animation(10, backgroundGuyS);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background1,0,0,null);
        animation.drawAnimation(g,-140,160,0);
        for(int i = 0; i < getMenuOptions().length; i++) {
            MenuOption op = getMenuOptions()[i];
            op.render(g);
        }
    }
    @Override
    public void updateMenu() {
        animation.runAnimation();
    }

}

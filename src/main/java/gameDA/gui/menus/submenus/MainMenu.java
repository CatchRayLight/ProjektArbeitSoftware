package gameDA.gui.menus.submenus;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.gui.menus.Menu;
import gameDA.gui.menus.MenuOption;
import gameDA.objects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends Menu {
    private final BufferedImageLoader loader = new BufferedImageLoader();
    private final BufferedImage background1 = loader.loadImage("/MainMenueBackground.png");
    private final BufferedImage background2 = loader.loadImage("/MainMenueBackground1.png");
    private final BufferedImage background3 = loader.loadImage("/MainMenueBackground2.png");

    private final Animation animation;
    private int yy;
    private int xx = Game.SCREEN_WIDTH *4;
    private Font customFont = loadFont();


    public MainMenu(MenuOption[] menuOptions){
        super(menuOptions);
        BufferedImage backgroundGuy2 = loader.loadImage("/BackgroundGuy2.png");
        BufferedImage backgroundGuy1 = loader.loadImage("/BackgroundGuy1.png");
        BufferedImage backgroundGuy = loader.loadImage("/BackgroundGuy.png");
        BufferedImage[] backgroundGuyS = {backgroundGuy, backgroundGuy1, backgroundGuy2};
        animation = new Animation(10, backgroundGuyS);
    }

    @Override
    public void render(Graphics g) {
        yy++;
        if(yy >= 2){
            xx--;
            yy= 0;
        }
        g.drawImage(background1, xx - (4 * Game.SCREEN_WIDTH), 0, null);
        g.drawImage(background2, xx-(3*Game.SCREEN_WIDTH), 0, null);
        g.drawImage(background3, xx-(2*Game.SCREEN_WIDTH), 0, null);
        g.drawImage(background1, xx - (Game.SCREEN_WIDTH), 0, null);
        if(xx <= Game.SCREEN_WIDTH) xx = 4*Game.SCREEN_WIDTH;
        animation.drawAnimation(g,-140,170,0);
        g.setColor(Color.GREEN);
        g.setFont(customFont.deriveFont(128f));
        g.drawString("Cool Game",Game.SCREEN_WIDTH/2-20, Game.SCREEN_HEIGHT/2 - 100);
        g.setFont(customFont.deriveFont(50f));
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

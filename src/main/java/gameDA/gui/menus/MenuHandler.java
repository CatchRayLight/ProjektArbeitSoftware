package gameDA.gui.menus;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.objects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuHandler {

    private Menu currentMenu;
    private Menu startMenu;
    private boolean up = false, down = false, enter = false;
    private Game game;
    private boolean loadBackground = true;
    private final BufferedImageLoader loader = new BufferedImageLoader();
    private final BufferedImage background1 = loader.loadImage("/MainMenueBackground.png");
    private final BufferedImage background2 = loader.loadImage("/MainMenueBackground1.png");
    private final BufferedImage background3 = loader.loadImage("/MainMenueBackground2.png");
    private final SpriteSheet backgroundImageS = new SpriteSheet(loader.loadImage("/backgroundTest.png"));
    private final BufferedImage background = backgroundImageS.getImage(1,1,2048,2048);
    private Camera camera;

    private final Animation animation;
    private int yy;
    private int xx = Game.SCREEN_WIDTH *4;

    private Font customFont = loadFont();



    public MenuHandler(Menu startMenu) {
        this.currentMenu = startMenu;
        this.game = Game.getGame();
        this.startMenu = startMenu;
        BufferedImage backgroundGuy2 = loader.loadImage("/BackgroundGuy2.png");
        BufferedImage backgroundGuy1 = loader.loadImage("/BackgroundGuy1.png");
        BufferedImage backgroundGuy = loader.loadImage("/BackgroundGuy.png");
        BufferedImage[] backgroundGuyS = {backgroundGuy, backgroundGuy1, backgroundGuy2};
        animation = new Animation(10, backgroundGuyS);
        currentMenu.startMusic();
    }

    public void update(){
        currentMenu.update(this);
        animation.runAnimation();
    }
    public void render(Graphics g){
        if(loadBackground) {
            //Lade Menu hintergrund
            yy++;
            if (yy >= 2) {
                xx--;
                yy = 0;
            }
            g.drawImage(background1, xx - (4 * Game.SCREEN_WIDTH), 0, null);
            g.drawImage(background2, xx - (3 * Game.SCREEN_WIDTH), 0, null);
            g.drawImage(background3, xx - (2 * Game.SCREEN_WIDTH), 0, null);
            g.drawImage(background1, xx - (Game.SCREEN_WIDTH), 0, null);
            if (xx <= Game.SCREEN_WIDTH) xx = 4 * Game.SCREEN_WIDTH;
            animation.drawAnimation(g, -140, 170, 0);
            g.setColor(Color.GREEN);
            g.setFont(customFont.deriveFont(128f));
            g.drawString("Cool Game", Game.SCREEN_WIDTH / 2 - 20, Game.SCREEN_HEIGHT / 2 - 100);
            g.setFont(customFont.deriveFont(50f));
        } else {
            //Lade Gameobjects zum rendern

            camera = game.getCamera();

            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.translate(-camera.getX(), -camera.getY());

            g.drawImage(background, 0, 0, null);
            game.getObjectHandler().render(g);
            graphics2D.translate(camera.getX(), camera.getY());
            g.setColor(Color.yellow);
            g.setFont(new Font("Courier New",Font.BOLD,10));
            g.drawString("Frames :" + game.getOutputFrames(),10,10);
        }
        currentMenu.render(g);
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isEnter() {
        return enter;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {

        if(currentMenu.getBackgroundMusic() != this.currentMenu.getBackgroundMusic()) {
            Game.getGame().getSound().stop();
            this.currentMenu = currentMenu;
            currentMenu.startMusic();
        } else this.currentMenu = currentMenu;

    }

    public void openEscapeMenu() {
        this.loadBackground = false;
        Game.getGame().getSound().stop();
        this.currentMenu = startMenu;
        currentMenu.startMusic();
        game.setGamestate(Gamestate.INMENU);
    }
    private Font loadFont(){
        try {
            //create the font to use. Specify the size!
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/resources/ARCADECLASSIC.TTF")).deriveFont(30f);
            return customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package gameDA.objects.model;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.submenus.DialogueMenu;
import gameDA.objects.Animation;
import gameDA.objects.GameObject;
import gameDA.objects.ObjectID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GasStationAttendant extends GameObject {
    private final Animation shopKeeperR;
    private final Animation shopKeeperL;
    private final Animation shopKeeperWait;
    private int counter;
    private int cooldown;
    public GasStationAttendant(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
        BufferedImage[] shopKeeperRimg = new BufferedImage[2];
        BufferedImage[] shopKeeperLimg = new BufferedImage[2];
        BufferedImage[] shopkeeperWaitimg = new BufferedImage[2];
        shopKeeperRimg[0] = spriteSheet.getImage(3,8,32,32);
        shopKeeperRimg[1] = spriteSheet.getImage(4,8,32,32);
        shopKeeperLimg[0] = spriteSheet.getImage(5,8,32,32);
        shopKeeperLimg[1] = spriteSheet.getImage(6,8,32,32);
        shopkeeperWaitimg[0] = spriteSheet.getImage(1,8,32,32);
        shopkeeperWaitimg[1] = spriteSheet.getImage(2,8,32,32);
        shopKeeperL = new Animation(10,shopKeeperLimg);
        shopKeeperR = new Animation(10,shopKeeperRimg);
        shopKeeperWait = new Animation(20,shopkeeperWaitimg);
    }

    @Override
    public void update() {
        cooldown++;
        shopKeeperR.runAnimation();
        shopKeeperWait.runAnimation();
        shopKeeperL.runAnimation();
        if(cooldown >= 100 && Game.getGame().getObjectHandler().isSpace()) {
            if (getBounds().intersects(Game.getGame().getObjectHandler().getPlayer().getBounds())) {
                Game.getGame().setGamestate(Gamestate.INMENU);
                if(Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().getFuel() != 15600) {
                    String[][] sampleDialogue = new String[][]{{"Tankwart Tobi"}, {"Ahh ",
                            "dein Tank ist ja fast Leer", "komm Bro ich mach ihn dir voll"}};
                    Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                    Game.getGame().getObjectHandler().getPlayer().setFuel(15600);
                    Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
                }else {
                    String[][] sampleDialogue = new String[][]{{"Tankwart Tobi"}, {"Dein tank ist aber diesmal voll",
                            "kennst du schon Robertos Tacos ", "vertrau mir die sind gut"}};
                    Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        counter++;
        if (counter <= 100) {
            x++;
            shopKeeperR.drawAnimation(g, x, y, 0);
        }
        if (counter <= 200 && counter >= 100) {
            x--;
            shopKeeperL.drawAnimation(g, x, y, 0);
        }
        if (counter <= 300 && counter >= 200) {
            shopKeeperWait.drawAnimation(g, x, y, 0);
        }
        if (counter <= 500 && counter >= 300) {
            counter = 0;
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }

    @Override
    public Rectangle getTopBounds(int offset) {
        return new Rectangle(x, y-offset, 32, 5);
    }

    @Override
    public Rectangle getRightBounds(int offset) {
        return new Rectangle(x+offset+27, y, 5, 32);
    }

    @Override
    public Rectangle getLeftBounds(int offset) {
        return new Rectangle(x - offset, y, 5, 32);
    }

    @Override
    public Rectangle getBotBounds(int offset) {
        return new Rectangle(x, y+27+ offset, 32, 5);
    }

}


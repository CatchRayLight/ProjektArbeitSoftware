package gameDA.objects.model.NPCS;

import gameDA.Game;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.submenus.DialogueMenu;
import gameDA.objects.Animation;
import gameDA.objects.ObjectID;
import gameDA.objects.model.NPC;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC3 extends NPC {
    private final Animation shopKeeperR;
    private final Animation shopKeeperL;
    private final Animation shopKeeperWait;
    private int counter;
    private int cooldown;
    public NPC3(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
        BufferedImage[] shopKeeperRimg = new BufferedImage[2];
        BufferedImage[] shopKeeperLimg = new BufferedImage[2];
        BufferedImage[] shopkeeperWaitimg = new BufferedImage[2];
        shopKeeperRimg[0] = spriteSheet.getImage(3,4,32,32);
        shopKeeperRimg[1] = spriteSheet.getImage(4,4,32,32);
        shopKeeperLimg[0] = spriteSheet.getImage(5,4,32,32);
        shopKeeperLimg[1] = spriteSheet.getImage(6,4,32,32);
        shopkeeperWaitimg[0] = spriteSheet.getImage(1,4,32,32);
        shopkeeperWaitimg[1] = spriteSheet.getImage(2,4,32,32);
        shopKeeperL = new Animation(10,shopKeeperLimg);
        shopKeeperR = new Animation(10,shopKeeperRimg);
        shopKeeperWait = new Animation(20,shopkeeperWaitimg);
    }

    @Override
    public void update() {
        shopKeeperR.runAnimation();
        shopKeeperWait.runAnimation();
        shopKeeperL.runAnimation();
        speak();
    }

    @Override
    public void render(Graphics g) {
        walk(g);
    }

    @Override
    protected void walk(Graphics g) {
        counter++;
        if (counter <= 50) {
            x++;
            y++;
            shopKeeperL.drawAnimation(g, x, y, 0);
        }
        if (counter <= 100 && counter >= 50) {
            x--;
            y--;
            shopKeeperR.drawAnimation(g, x, y, 0);
        }
        if (counter <= 500 && counter >= 100) {
            shopKeeperWait.drawAnimation(g, x, y, 0);
        }
        if (counter <= 1000 && counter >= 500) {
            counter = 0;
        }
    }

    @Override
    protected void speak() {
        cooldown++;
        if(cooldown >= 100 && Game.getGame().getObjectHandler().isSpace()) {
            if (getBounds().intersects(Game.getGame().getObjectHandler().getPlayer().getBounds())) {
                Game.getGame().setGamestate(Gamestate.INMENU);
                if(Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().getHp() !=100) {
                    String[][] sampleDialogue = new String[][]{{"Hobby Arzt John"}, {"Oof ",
                            "du siehst aber ramponiert aus", "komm Bro ich verarzt dich"}};
                    Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                    Game.getGame().getObjectHandler().getPlayer().setHp(100);
                    Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
                }else {
                    String[][] sampleDialogue = new String[][]{{"Hobby Arzt Tobi John"}, {"Haja siehst gut aus",
                            "Pass auf dich auf im Space", "und bring mir mal was cooles mit"}};
                    Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                }
            }
        }
    }
}

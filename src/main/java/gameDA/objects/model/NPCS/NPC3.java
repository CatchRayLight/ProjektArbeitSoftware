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
    private final Animation NPCR;
    private final Animation NPCL;
    private final Animation NPCWAIT;
    private int counter;
    private int cooldown;
    public NPC3(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
        BufferedImage[] NCPRimg = new BufferedImage[2];
        BufferedImage[] NPCLimg = new BufferedImage[2];
        BufferedImage[] NPCWaitImg = new BufferedImage[2];
        NCPRimg[0] = spriteSheet.getImage(3,4,32,32);
        NCPRimg[1] = spriteSheet.getImage(4,4,32,32);
        NPCLimg[0] = spriteSheet.getImage(5,4,32,32);
        NPCLimg[1] = spriteSheet.getImage(6,4,32,32);
        NPCWaitImg[0] = spriteSheet.getImage(1,4,32,32);
        NPCWaitImg[1] = spriteSheet.getImage(2,4,32,32);
        NPCL = new Animation(10,NPCLimg);
        NPCR = new Animation(10,NCPRimg);
        NPCWAIT = new Animation(20,NPCWaitImg);
    }

    @Override
    public void update() {
        NPCR.runAnimation();
        NPCWAIT.runAnimation();
        NPCL.runAnimation();
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
            NPCL.drawAnimation(g, x, y, 0);
        }
        if (counter <= 100 && counter >= 50) {
            x--;
            y--;
            NPCR.drawAnimation(g, x, y, 0);
        }
        if (counter <= 500 && counter >= 100) {
            NPCWAIT.drawAnimation(g, x, y, 0);
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

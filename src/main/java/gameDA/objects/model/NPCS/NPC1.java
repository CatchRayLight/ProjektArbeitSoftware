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

public class NPC1 extends NPC {
    private final Animation NPCR;
    private final Animation NPCL;
    private final Animation NPCWAIT;
    private int counter;
    private int cooldown;
    public NPC1(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
        BufferedImage[] NPCRimg = new BufferedImage[2];
        BufferedImage[] NPCLimg = new BufferedImage[2];
        BufferedImage[] NPCWaitImg = new BufferedImage[2];
        NPCRimg[0] = spriteSheet.getImage(3,8,32,32);
        NPCRimg[1] = spriteSheet.getImage(4,8,32,32);
        NPCLimg[0] = spriteSheet.getImage(5,8,32,32);
        NPCLimg[1] = spriteSheet.getImage(6,8,32,32);
        NPCWaitImg[0] = spriteSheet.getImage(1,8,32,32);
        NPCWaitImg[1] = spriteSheet.getImage(2,8,32,32);
        NPCL = new Animation(10,NPCLimg);
        NPCR = new Animation(10,NPCRimg);
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
        if (counter <= 100) {
            x++;
            NPCR.drawAnimation(g, x, y, 0);
        }
        if (counter <= 200 && counter >= 100) {
            x--;
            NPCL.drawAnimation(g, x, y, 0);
        }
        if (counter <= 300 && counter >= 200) {
            NPCWAIT.drawAnimation(g, x, y, 0);
        }
        if (counter <= 500 && counter >= 300) {
            counter = 0;
        }
    }
    @Override
    protected void speak() {
        cooldown++;
        if (cooldown >= 100 && Game.getGame().getObjectHandler().isSpace()) {
            if (getBounds().intersects(Game.getGame().getObjectHandler().getPlayer().getBounds())) {
                Game.getGame().setGamestate(Gamestate.INMENU);
                if (Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().getFuel() != 15600) {
                    String[][] sampleDialogue = new String[][]{{"Tankwart Tobi"}, {"Ahh ",
                            "dein Tank ist ja fast Leer", "komm Bro ich mach ihn dir voll"}};
                    Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                    Game.getGame().getObjectHandler().getPlayer().setFuel(15600);
                    Game.getGame().getObjectHandler().getPlayer().getPlayerHealthbar().update();
                } else {
                    String[][] sampleDialogue = new String[][]{{"Tankwart Tobi"}, {"Dein tank ist aber diesmal voll",
                            "kennst du schon Robertos Tacos ", "vertrau mir die sind gut"}};
                    Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
                }
            }
        }
    }
}

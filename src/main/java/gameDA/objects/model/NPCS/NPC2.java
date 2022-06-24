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

public class NPC2 extends NPC {
    private final Animation NPCR;
    private final Animation NPCL;
    private final Animation NPCWAIT;
    private int counter;
    private int cooldown;
    public NPC2(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
        BufferedImage[] NPCRimg = new BufferedImage[2];
        BufferedImage[] NPCLimg = new BufferedImage[2];
        BufferedImage[] NPCWaitImg = new BufferedImage[2];
        NPCRimg[0] = spriteSheet.getImage(3,5,32,32);
        NPCRimg[1] = spriteSheet.getImage(4,5,32,32);
        NPCLimg[0] = spriteSheet.getImage(5,5,32,32);
        NPCLimg[1] = spriteSheet.getImage(6,5,32,32);
        NPCWaitImg[0] = spriteSheet.getImage(1,5,32,32);
        NPCWaitImg[1] = spriteSheet.getImage(2,5,32,32);
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
            y++;
            NPCL.drawAnimation(g, x, y, 0);
        }
        if (counter <= 600 && counter >= 100) {
            NPCWAIT.drawAnimation(g, x, y, 0);
        }
        if (counter <= 700 && counter >= 600) {
            y--;
            NPCR.drawAnimation(g, x, y, 0);
        }
        if (counter <= 1500 && counter >= 700) {
            NPCWAIT.drawAnimation(g, x, y, 0);
        }
        if (counter <= 1600 && counter >= 1500) {
            counter = 0;
        }
    }

    @Override
    protected void speak() {
        cooldown++;
        if(cooldown >= 100 && Game.getGame().getObjectHandler().isSpace()) {
            if (getBounds().intersects(Game.getGame().getObjectHandler().getPlayer().getBounds())) {
                Game.getGame().setGamestate(Gamestate.INMENU);
                String[][] sampleDialogue = new String[][]{{"Chilliger Randy"}, {"Ahh ich bin so traurig  ",
                        " mein Fisch wurde mir heute von den Schuften aus dem Space geklaut", "Kannst du mir ihn bitte wieder holen"}};
                Game.getGame().getMenuHandler().setCurrentMenu(new DialogueMenu(sampleDialogue));
            }
        }
    }
}

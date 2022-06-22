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
    private final Animation shopKeeperR;
    private final Animation shopKeeperL;
    private final Animation shopKeeperWait;
    private int counter;
    private int cooldown;
    public NPC2(int x, int y, ObjectID id, SpriteSheet spriteSheet) {
        super(x, y, id, spriteSheet);
        BufferedImage[] shopKeeperRimg = new BufferedImage[2];
        BufferedImage[] shopKeeperLimg = new BufferedImage[2];
        BufferedImage[] shopkeeperWaitimg = new BufferedImage[2];
        shopKeeperRimg[0] = spriteSheet.getImage(3,5,32,32);
        shopKeeperRimg[1] = spriteSheet.getImage(4,5,32,32);
        shopKeeperLimg[0] = spriteSheet.getImage(5,5,32,32);
        shopKeeperLimg[1] = spriteSheet.getImage(6,5,32,32);
        shopkeeperWaitimg[0] = spriteSheet.getImage(1,5,32,32);
        shopkeeperWaitimg[1] = spriteSheet.getImage(2,5,32,32);
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
        if (counter <= 100) {
            y++;
            shopKeeperL.drawAnimation(g, x, y, 0);
        }
        if (counter <= 600 && counter >= 100) {
            shopKeeperWait.drawAnimation(g, x, y, 0);
        }
        if (counter <= 700 && counter >= 600) {
            y--;
            shopKeeperR.drawAnimation(g, x, y, 0);
        }
        if (counter <= 1500 && counter >= 700) {
            shopKeeperWait.drawAnimation(g, x, y, 0);
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

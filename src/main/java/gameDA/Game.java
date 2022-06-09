package gameDA;


import gameDA.config.output.BufferedImageLoader;
import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.GameWindow;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.*;
import gameDA.gui.menus.submenus.DialogueMenu;
import gameDA.gui.menus.submenus.MainMenu;
import gameDA.gui.menus.submenus.OptionsMenu;
import gameDA.gui.menus.submenus.SaveMenu;

import gameDA.objects.*;
import gameDA.objects.model.Event;
import gameDA.objects.model.LootBox;
import gameDA.objects.model.SpaceEnemy;
import gameDA.objects.model.Walls;
import gameDA.objects.model.Player;
import gameDA.config.input.KeyListener;
import gameDA.savemanager.Save;
import gameDA.savemanager.SaveKey;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;


public class Game extends Canvas implements Runnable {

    public static final int SCREEN_WIDTH = 1216;
    public static final int SCREEN_HEIGHT = 928;
    private boolean isRunning = false;
    private Thread thread;
    private final ObjectHandler objectHandler;
    private final BufferedImage[] background =new BufferedImage[4];

    private final Camera camera;
    private Gamestate gamestate;
    private MenuHandler menuHandler;
    private final KeyListener keyListener;
    private final SpriteSheet spriteS;
    private int outputFrames;
    private final boolean onPlanet;
    public static Healthbar healthbar = null;




    public Game() {
        new GameWindow(SCREEN_HEIGHT, SCREEN_WIDTH, "Space Plugg", this);
        start();
        objectHandler = new ObjectHandler();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage testLvL = loader.loadImage("/TestLVL.png");
        BufferedImage spriteSheet = loader.loadImage("/SpriteSheet.png");
        BufferedImage backgroundImage = loader.loadImage("/backgroundTest.png");
        spriteS = new SpriteSheet(spriteSheet);
        SpriteSheet backgroundImageS = new SpriteSheet(backgroundImage);
        camera = new Camera(0, 0);
        healthbar = new Healthbar((int)camera.getX(),(int)camera.getY(),spriteS, 100,90,100);
        keyListener = new KeyListener(objectHandler, menuHandler, gamestate);
        this.addKeyListener(keyListener);


        background[0] = backgroundImageS.getImage(1,1,2048,2048);



        onPlanet = false; //toggle for player model change off/on planet
        levelBuilder(testLvL);
        //Add new obj

    }

    public void updateGamestate(Gamestate newgamestate) {
        gamestate = newgamestate;
        keyListener.setGamestate(gamestate);
    }

    private void stop() throws InterruptedException {
        isRunning = false;
        thread.join();
    }

    private void start() {
        initMenus();
        initSafeManager();
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void initSafeManager() {
        //For testing (currently)
        Save save = null;
        try {
            save = new Save(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "save.txt");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        save.safe(SaveKey.PLAYERX, "5");
    }
    private void initMenus() {
        gamestate = Gamestate.INMENU;
        MenuOption[] empty = {};
        //initinalize empty
        OptionsMenu optionsMenu = new OptionsMenu(empty);
        MainMenu mainMenu = new MainMenu(empty);
        SaveMenu saveMenu = new SaveMenu(empty);
        //menuoptions
        //StartMenu
        MenuOption[] menuOptionsStartmenu = {new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Play", 100, 100),new MenuOption(() -> {
            menuHandler.setCurrentMenu(saveMenu);
        }, "Saves",100,250),new MenuOption(() -> {
            menuHandler.setCurrentMenu(optionsMenu);
        }, "Options",100,400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(new DialogueMenu(new String[]{"Hello", "It works"}, this));
        }, "Exit",100,550)
        };

        //OptionsMenu
        MenuOption[] menuOptionsOptionsMenu = {new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Option1", 100, 100), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Option2",100,250), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Option3",100,400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(mainMenu);
        }, "Back",100,550)
        };

        //SaveMenu
        MenuOption[] menuOptionsSaveMenu = {new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Save 1", 100, 100), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Save 2",100,250), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Save 3",100,400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(mainMenu);
        }, "Back",100,550)
        };
        //set new menuoptions
        mainMenu.setMenuOptions(menuOptionsStartmenu);
        optionsMenu.setMenuOptions(menuOptionsOptionsMenu);
        saveMenu.setMenuOptions(menuOptionsSaveMenu);

        menuHandler = new MenuHandler(mainMenu, this);
    }

    //game-Loop

    @Override
    public void run() {
        /**
         * lastime: Time since the last iteration of the loop. Helps compute delta.
         * AmountOfTicks: The max FPS for the game.
         * ns: The number of nanoseconds per frame.
         * delta: The 'progress' that must be elapsed until the next frame.
         * frames: The number of frames elapsed since the last time we displayed the FPS.
         * time: The current time. Used to know when to display next the FPS.
         **/
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) /ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                //updates++;
                delta--;
                render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                outputFrames = frames;
                frames = 0;
                //updates = 0;
            }
        }
        try {
            stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (gamestate.equals(Gamestate.INGAME)) {
            for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
                if (objectHandler.gameObjects.get(i).getId() == ObjectID.PLAYER) {
                    camera.update(objectHandler.gameObjects.get(i));
                }
            }
            objectHandler.update();
            healthbar.update();
        }
        if (gamestate.equals(Gamestate.INMENU)) {
            if(camera != null) {
                camera.setX(0);
                camera.setY(0);
            }
            menuHandler.update(this);
            objectHandler.update();

        }
    }

    public void render() {
        //starten bei null
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        //die tatsächlichen Frames sind schon vor dem Anzeigen da, "Preloaded", also bswp. 1ster Frame wird gezeigt
        // 2 andere sind schon in der Warteschlange dahinter zum zeigen
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        //ab hier werden die Objecte, Player, Walls etc auf den canvas gerendert
        switch (gamestate) {
            case INGAME:
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.translate(-camera.getX(), -camera.getY());
                g.drawImage(background[0], 0, 0, null);

                objectHandler.render(g);
                graphics2D.translate(camera.getX(), camera.getY());
                healthbar.render(g);
                g.setColor(Color.yellow);
                g.setFont(new Font("Courier New",Font.BOLD,10));
                g.drawString("Frames :" + outputFrames,10,10);
                break;
            case INMENU:
                menuHandler.render(g);
                break;
        }
        g.dispose();
        bufferStrategy.show();
    }
    // level loader durch rbg differenzierung, png wird eingelesen und dann mit objecten verwiesen
    private void levelBuilder(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int xAxis = 0; xAxis < width; xAxis++) {
            for (int yAxis = 0; yAxis < height; yAxis++) {
                int pixel = image.getRGB(xAxis, yAxis);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255 && green != 255) {
                    objectHandler.addObj(new Walls(xAxis * 32, yAxis * 32, ObjectID.BLOCK,spriteS,onPlanet));
                }
                if (blue == 255 && green != 255) {
                    objectHandler.addObj(new Player(xAxis * 32, yAxis * 32, ObjectID.PLAYER, spriteS, objectHandler, onPlanet, healthbar));
                }
                if(green == 255 && blue != 255){
                    objectHandler.addObj(new SpaceEnemy(xAxis * 32 , yAxis * 32, ObjectID.ENEMY,spriteS,objectHandler));
                }
                if(green == 255 && blue == 255){
                    //cyan
                    objectHandler.addObj(new LootBox(xAxis * 32 , yAxis * 32, ObjectID.LOOTBOX,spriteS,objectHandler));
                }
                if(red == 255  && green == 255){
                    //yel
                    objectHandler.addObj(new Event(xAxis * 32, yAxis * 32, ObjectID.EVENT, spriteS, objectHandler));
                }
            }
        }
    }
}

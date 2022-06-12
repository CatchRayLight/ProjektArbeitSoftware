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

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {

    //Variablen und ressourcen benötigt für das Spiel
    public static final int SCREEN_WIDTH = 1216;
    public static final int SCREEN_HEIGHT = 928;
    private final BufferedImage[] background =new BufferedImage[4];
    private final SpriteSheet spriteS;

    //Objekte zur Steuerung des Spieles
    private MenuHandler menuHandler;
    private final ObjectHandler objectHandler;
    private final KeyListener keyListener;
    private Sound sound;
    private final Camera camera;
    private Thread thread;

    //Variablen die Auskunft über den Zustand des Spieles geben
    private Gamestate gamestate = Gamestate.INMENU;
    private int outputFrames;
    private final boolean onPlanet;
    private boolean isRunning = false;

    //Game Instanz
    private static Game game;

    //Konstruktor
    public Game() {
        //Laden der Ressourcen
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage testLvL = loader.loadImage("/TestLVL.png");
        BufferedImage LvL1 = loader.loadImage("/Level2.png");
        BufferedImage spriteSheet = loader.loadImage("/SpriteSheet.png");
        BufferedImage backgroundImage = loader.loadImage("/backgroundTest.png");
        BufferedImage icon = loader.loadImage("/Icon.png");

        //Initialisierungen
        game = this;
        GameWindow gameWindow = new GameWindow(SCREEN_HEIGHT, SCREEN_WIDTH, "Space Plugg");
        gameWindow.setIconImage(icon);
        sound = new Sound();
        objectHandler = new ObjectHandler();
        initMenus(); //Initialisiert alle nötigen Menus und Menuhandler
        spriteS = new SpriteSheet(spriteSheet);
        onPlanet = false; //toggle for player model change off/on planet
        camera = new Camera(0, 0);
        keyListener = new KeyListener(objectHandler, menuHandler, gamestate);
        this.addKeyListener(keyListener);
        SpriteSheet backgroundImageS = new SpriteSheet(backgroundImage);
        background[0] = backgroundImageS.getImage(1,1,2048,2048);

        //Starten des Spieles und laden des Levels
        start();
        levelBuilder(LvL1);
    }

    //Die Start Methode startet den Thread und somit das Spiel
    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    //Die Stop methode beendet den Thread
    private void stop() throws InterruptedException {
        isRunning = false;
        thread.join();
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

    //update methode
    public void update() {
        if (gamestate.equals(Gamestate.INGAME)) {
            for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
                if (objectHandler.gameObjects.get(i).getId() == ObjectID.PLAYER) {
                    camera.update(objectHandler.gameObjects.get(i));
                }
            }
            objectHandler.update();
        }
        if (gamestate.equals(Gamestate.INMENU)) {
            if(camera != null) {
                camera.setX(0);
                camera.setY(0);
            }
            menuHandler.update();
            objectHandler.update();

        }
    }

    //Render des gesamten Spielinhaltes
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

        //ab hier werden die Objecte, Player, Walls etc auf den canvas gerendert
        switch (gamestate) {
            case INGAME:
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.translate(-camera.getX(), -camera.getY());

                g.drawImage(background[0], 0, 0, null);
                objectHandler.render(g);
                graphics2D.translate(camera.getX(), camera.getY());
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

                if (blue == 255 && green != 255 && red != 255) {
                    objectHandler.addObj(new Player(xAxis * 32, yAxis * 32, ObjectID.PLAYER, spriteS,
                            objectHandler, onPlanet, camera, 100, 90, 100, 6,
                            10,10,20));
                }
                if (red == 255 && green != 255 && blue != 255) {
                    objectHandler.addObj(new Walls(xAxis * 32, yAxis * 32, ObjectID.BLOCK, spriteS, onPlanet));
                }
                if (green == 255 && blue != 255 && red != 255) {
                    objectHandler.addObj(new SpaceEnemy(xAxis * 32, yAxis * 32, ObjectID.ENEMY, spriteS, objectHandler, 100));
                }
                if (green == 255 && blue == 255 && red != 255) {
                    //cyan
                    objectHandler.addObj(new LootBox(xAxis * 32, yAxis * 32, ObjectID.LOOTBOX, spriteS, objectHandler));
                }
                if (red == 255 && green == 255 && blue != 255) {
                    //yel
                    objectHandler.addObj(new Event(xAxis * 32, yAxis * 32, ObjectID.EVENT, spriteS, objectHandler));
                }
            }
        }
    }

    //Initialisierung der Menus
    private void initMenus() {
        MenuOption[] empty = {};
        //initinalize empty
        OptionsMenu optionsMenu = new OptionsMenu(empty);
        MainMenu mainMenu = new MainMenu(empty);
        SaveMenu saveMenu = new SaveMenu(empty);
        //menuoptions
        //StartMenu
        MenuOption[] menuOptionsStartmenu = {new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Play", 100, 100),new MenuOption(() -> {
            menuHandler.setCurrentMenu(saveMenu);
        }, "Saves",100,250),new MenuOption(() -> {
            menuHandler.setCurrentMenu(optionsMenu);
        }, "Options",100,400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(new DialogueMenu(new String[][]{{"This is first line dialogue option 1", "This is second line dialogue option 1", "This is third line dialogue option 1"},{"This is first line dialogue option 2"}}));
        }, "Exit",100,550)
        };

        //OptionsMenu
        MenuOption[] menuOptionsOptionsMenu = {new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Option1", 100, 100), new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Option2",100,250), new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Option3",100,400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(mainMenu);
        }, "Back",100,550)
        };

        //SaveMenu
        MenuOption[] menuOptionsSaveMenu = {new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Save 1", 100, 100), new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Save 2",100,250), new MenuOption(() -> {
            Game.getGame().getSound().stop();
            setGamestate(Gamestate.INGAME);
        }, "Save 3",100,400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(mainMenu);
        }, "Back",100,550)
        };
        //set new menuoptions
        mainMenu.setMenuOptions(menuOptionsStartmenu);
        optionsMenu.setMenuOptions(menuOptionsOptionsMenu);
        saveMenu.setMenuOptions(menuOptionsSaveMenu);

        menuHandler = new MenuHandler(mainMenu);

    }

    //Getter and setter


    public void setGamestate(Gamestate newgamestate) {
        gamestate = newgamestate;
        keyListener.setGamestate(gamestate);
    }

    public MenuHandler getMenuHandler() {
        return menuHandler;
    }

    public Camera getCamera() {
        return camera;
    }

    public ObjectHandler getObjectHandler() {
        return objectHandler;
    }

    public int getOutputFrames() {
        return outputFrames;
    }
    
    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public static Game getGame() {
        return game;
    }



}

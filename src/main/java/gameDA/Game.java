package gameDA;


import gameDA.config.output.BufferedImageLoader;
import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.GameWindow;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.MenuHandler;
import gameDA.gui.menus.MenuOption;
import gameDA.gui.menus.OptionsMenu;
import gameDA.gui.menus.StartMenu;
import gameDA.objects.*;
import gameDA.objects.model.Block;
import gameDA.objects.model.Player;
import gameDA.config.input.KeyListener;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {

    private final int SCREEN_WIDTH = 1216;
    private final int SCREEN_HEIGHT = 928;
    private boolean isRunning = false;
    private Thread thread;
    private final ObjectHandler objectHandler;
    private BufferedImage testLvL = null;
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;
    private Camera camera;
    private Gamestate gamestate;
    private MenuHandler menuHandler;
    private KeyListener keyListener;
    private SpriteSheet spriteS;
    private int outputFrames;



    public Game() {
        new GameWindow(SCREEN_HEIGHT, SCREEN_WIDTH, "Space Plugg", this);
        start();
        objectHandler = new ObjectHandler();
        camera = new Camera(0, 0);
        keyListener = new KeyListener(objectHandler, menuHandler, gamestate);
        this.addKeyListener(keyListener);

        BufferedImageLoader loader = new BufferedImageLoader();
        testLvL = loader.loadImage("/TestLVL.png");
        spriteSheet = loader.loadImage("/SpriteSheet.png");
        spriteS = new SpriteSheet(spriteSheet);
        background = spriteS.getImage(5,1,32,32);




        loadLevel(testLvL);
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
        isRunning = true;
        thread = new Thread(this);
        thread.start();

    }

    private void initMenus() {
        gamestate = Gamestate.INMENU;
        MenuOption[] empty = {};
        //initinalize empty
        OptionsMenu optionsMenu = new OptionsMenu(empty);
        StartMenu startMenu = new StartMenu(empty);
        //menuoptions
        MenuOption[] menuOptionsStartmenu = {new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Play", 100, 100), new MenuOption(() -> {
            menuHandler.setCurrentMenu(optionsMenu);
            System.out.println(optionsMenu.toString());
        }, "Options",250,100), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Exit",400,100)
        };

        MenuOption[] menuOptionsOptionsMenu = {new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Option1", 100, 100), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Option2",250,100), new MenuOption(() -> {
            updateGamestate(Gamestate.INGAME);
        }, "Option3",400,100)
        };
        //set new menuoptions
        startMenu.setMenuOptions(menuOptionsStartmenu);
        optionsMenu.setMenuOptions(menuOptionsOptionsMenu);

        menuHandler = new MenuHandler(startMenu);
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

        }
        if (gamestate.equals(Gamestate.INMENU)) {
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
        g.setColor(Color.white);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        //ab hier werden die Objecte, Player, Walls etc auf den canvas gerendert
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.translate(-camera.getX(), -camera.getY());
        switch (gamestate) {
            case INGAME:
                for (int i = 0; i < SCREEN_WIDTH+ 832; i+=32) {
                    for (int j = 0; j < SCREEN_HEIGHT+ 1104; j+=32) {
                        g.drawImage(background,i,j,null);
                    }
                }
                objectHandler.render(g);
                break;
            case INMENU:
                menuHandler.render(g);
                break;
        }
        graphics2D.translate(camera.getX(), camera.getY());
        g.setColor(Color.yellow);
        g.setFont(new Font("Courier New",Font.BOLD,10));
        g.drawString("Frames :" + outputFrames,10,10);
        g.dispose();
        bufferStrategy.show();

    }

    // level loader durch rbg differenzierung, png wird eingelesen und dann mit objecten verwiesen
    private void loadLevel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int xAxis = 0; xAxis < width; xAxis++) {
            for (int yAxis = 0; yAxis < height; yAxis++) {
                int pixel = image.getRGB(xAxis, yAxis);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255) {
                    objectHandler.addObj(new Block(xAxis * 32, yAxis * 32, ObjectID.BLOCK,spriteS));
                }
                if (blue == 255) {
                    objectHandler.addObj(new Player(xAxis * 32, yAxis * 32, ObjectID.PLAYER, spriteS, objectHandler));
                }
                if(green == 255){

                }
            }
        }
    }



}

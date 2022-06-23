package gameDA;


import gameDA.config.output.BufferedImageLoader;
import gameDA.config.output.Camera;
import gameDA.config.output.SpriteSheet;
import gameDA.gui.GameWindow;
import gameDA.gui.Gamestate;
import gameDA.gui.menus.*;
import gameDA.gui.menus.submenus.*;

import gameDA.objects.*;
import gameDA.objects.model.*;
import gameDA.config.input.KeyListener;
import gameDA.objects.model.NPCS.NPC1;
import gameDA.objects.model.NPCS.NPC2;
import gameDA.objects.model.NPCS.NPC3;
import gameDA.objects.model.NPCS.ShopKeeper;
import gameDA.savemanager.Options;
import gameDA.savemanager.SafeManager;
import gameDA.savemanager.Save;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {

    //Variablen und ressourcen benötigt für das Spiel
    public static final int SCREEN_WIDTH = 1216;
    public static final int SCREEN_HEIGHT = 928;
    private final BufferedImage[] backgroundImage = new BufferedImage[6];
    private final SpriteSheet spriteS;
    private final int[] xBoss = {-20,-30,320};
    private final int[] yBoss = {0,-30,100};

    //Objekte zur Steuerung des Spieles
    private final SafeManager safeManager;
    private MenuHandler menuHandler;
    private Options options;
    private final ObjectHandler objectHandler;
    private final KeyListener keyListener;
    private Sound sound;
    private final Camera camera;
    private Thread thread;
    private final LvLHandler lvLHandler;
    private final Save[] saves = new Save[]{
            new Save("saves","Save1.txt"), new Save("saves","Save2.txt"),
            new Save("saves","Save3.txt"), new Save("saves","config.txt")
    };

    //Variablen die Auskunft über den Zustand des Spieles geben
    public static Gamestate gamestate = Gamestate.INMENU;
    private boolean onPlanet, bossLvl;

    private int LvLInt;
    private boolean isRunning = false;

    //Game Instanz
    private static Game game;

    //Konstruktor
    public Game() {
        //Laden der Ressourcen
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage spriteSheet = loader.loadImage("/textures/SpriteSheet.png");
        backgroundImage[0] = loader.loadImage("/maps/backgroundTest.png");
        backgroundImage[1] = loader.loadImage("/maps/Planet1.png");
        backgroundImage[2] = loader.loadImage("/maps/Boss1.png");
        backgroundImage[3] = loader.loadImage("/maps/Boss2.png");
        backgroundImage[4] = loader.loadImage("/maps/Boss3.png");
        backgroundImage[5] = loader.loadImage("/maps/WinnLevel.png");
        //Initialisierungen
        game = this;
        new GameWindow(SCREEN_HEIGHT, SCREEN_WIDTH, "Space Mission", loader);//kreiert neue Leinwand
        sound = new Sound();
        objectHandler = new ObjectHandler(); //initialisiert objectHandler
        lvLHandler = new LvLHandler(); //initialisiert lvlHandler
        options = new Options(true, true); //Belade options mit Startwerten (falls keine gespeicherten Options verfügbar sind)
        safeManager = new SafeManager(saves);
        options = safeManager.loadOptions(); //Belade Options mit gespeicherten Options
        initMenus(); //Initialisiert alle nötigen Menus und Menuhandler
        spriteS = new SpriteSheet(spriteSheet);//initialisiert Karte mit Bildern ein (konvertet sie in SpriteSheet)
        camera = new Camera(0, 0);//es wird eine Neue Camera initialisiert
        keyListener = new KeyListener(objectHandler, menuHandler, gamestate);//Keylistener initialisiert
        this.addKeyListener(keyListener);//Keylistener auf Canvas zugefügt
        //Starten des Spieles und laden des Levels
        start();
        //setting the Start Level on 1 and declaring onPlanet boolean
        setOnPlanet(true);
        levelBuilder(lvLHandler.getLvLImage(getLvLInt()));

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
        System.exit(0);
        thread.join();
    }
    //game-Loop
    /**
     * "Frame": Das gerade angezeigte Bild
     * lastime: Die Zeit seit der letzten Wiederholung der Schleife.
     * AmountOfTicks: Maximale Tick rate, Frequenz wie oft in der Sekunde erneuert wird
     * ns: Wie viele Nanosekunden per Frame verlaufen
     * delta: Der Fortschritt der geschehen muss bis zum nächsten Frame
     * frames: Wie viele Frames in einer Sekunde gezeigt wurden
     * time: Die derzeitige Zeit,sie wird benötigt um zu Wissen, wann der nächste Frame gezeigt wird.
     **/
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                delta--;
                update();
                render();
                frames++;
                if (System.currentTimeMillis() - timer > 1000) {
                    System.out.println(frames);
                    timer += 1000;
                    frames = 0;
                }
            }
        }
        try {
            stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //update methode
    public void update() {
        if (gamestate.equals(Gamestate.INGAME)) {
            for (int i = 0; i < objectHandler.gameObjects.size(); i++) {
                if (objectHandler.gameObjects.get(i).getId() == ObjectID.PLAYER) {
                    //updating camera to player if in space
                    if(!isOnPlanet())camera.update(objectHandler.gameObjects.get(i));
                    //updating camera if on planet to fixed value
                    if(isOnPlanet() || getLvLInt() == 2 || getLvLInt() == 5|| getLvLInt() == 8){
                        camera.setX(0);
                        camera.setY(0);
                    }
                }
            }
            objectHandler.update();
        }
        if (gamestate.equals(Gamestate.INMENU)) {
            menuHandler.update();
        }
    }

    //Render des gesamten Spielinhaltes
    public void render() {
        //bilder werden nacheinander geladen
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(2);
            return;
        }
        Graphics g = bufferStrategy.getDrawGraphics();
        switch (gamestate) {
            case INGAME:
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.translate(-camera.getX(), -camera.getY());
                drawBackground(g);
                objectHandler.render(g);
                graphics2D.translate(camera.getX(), camera.getY());
                g.setColor(Color.yellow);
                g.setFont(new Font("Courier New",Font.BOLD,10));
                g.drawString("Level :" + getLvLInt() / 3,10,10);
                break;
            case INMENU:
                menuHandler.render(g);
                break;
        }
        g.dispose();
        bufferStrategy.show();
    }

    // level loader durch rbg differenzierung, png wird eingelesen und dann mit objecten verwiesen
    public void levelBuilder(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int xAxis = 0; xAxis < width; xAxis++) {
            for (int yAxis = 0; yAxis < height; yAxis++) {
                int pixel = image.getRGB(xAxis, yAxis);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (!isBossLvl()) {
                    if (blue == 255 && green != 255 && red != 255) {
                        if (getLvLInt() == 0) {
                            //blue
                            objectHandler.addObj(new Player(xAxis * 32, yAxis * 32, ObjectID.PLAYER, spriteS,
                                    objectHandler, isOnPlanet(), camera, 100, 90, 15600, 9,
                                    5, 10, 20, 0));
                        }
                    }
                    if (!isOnPlanet()) {
                        //green
                        if (green == 255 && blue != 255 && red != 255) {
                            objectHandler.addObj(new SpaceEnemy(xAxis * 32, yAxis * 32, ObjectID.ENEMY, spriteS,
                                    objectHandler, 100, 3 + getLvLInt(), 10, 2 * getLvLInt()));
                        }
                        //cyan
                        if (green == 255 && blue == 255 && red != 255) {
                            objectHandler.addObj(new LootBox(xAxis * 32, yAxis * 32, ObjectID.LOOTBOX, spriteS, objectHandler));
                        }
                    } else {
                        //green
                        if (red == 255 && blue == 255 && green != 255) {
                            objectHandler.addObj(new NPC1(xAxis * 32, yAxis * 32, ObjectID.ENTITY, spriteS));
                        }
                        //cyan
                        if (green == 255 && blue == 255 && red != 255) {
                            objectHandler.addObj(new NPC3(xAxis * 32, yAxis * 32, ObjectID.ENTITY, spriteS));
                        }
                        //green
                        if (green == 255 && blue != 255 && red != 255) {
                            objectHandler.addObj(new NPC2(xAxis * 32, yAxis * 32, ObjectID.ENTITY, spriteS));
                        }
                        //white
                        if (red == 255 && green == 255 && blue == 255) {
                            objectHandler.addObj(new ShopKeeper(xAxis * 32, yAxis * 32, ObjectID.ENTITY, spriteS));
                        }
                    }
                    //yellow
                    if (red == 255 && green == 255 && blue != 255) {
                        objectHandler.addObj(new EventTeleportLVL(xAxis * 32, yAxis * 32, ObjectID.EVENT, spriteS, objectHandler));
                    }
                }
                //white
                if(isBossLvl() && !isOnPlanet()) {
                    if (red == 255 && green == 255 && blue == 255) {
                        objectHandler.addObj(new SpaceBoss((xAxis * 32) + xBoss[getLvLInt()/3], (yAxis * 32) + yBoss[getLvLInt()/3], ObjectID.SPACEBOSS,
                                    spriteS, objectHandler, 1200, 6 + getLvLInt(), 9 - getLvLInt(), 5 * getLvLInt()));
                    }
                }
                //red
                if (red == 255 && green != 255 && blue != 255) {
                    objectHandler.addObj(new Walls(xAxis * 32, yAxis * 32, ObjectID.BLOCK, spriteS));
                }
            }
        }
    }
    private void drawBackground(Graphics g){
        g.drawImage(backgroundImage[1], 0, 0, null);
        if(getLvLInt() == 2)g.drawImage(backgroundImage[2],0,0,null);
        if(getLvLInt() == 5)g.drawImage(backgroundImage[3],0,0,null);
        if(getLvLInt() == 8)g.drawImage(backgroundImage[4],0,0,null);
        if(getLvLInt() == 1||getLvLInt() == 4||getLvLInt() == 7){
            g.drawImage(backgroundImage[0],0,0,null);
        }
        if(getLvLInt() >= 9){
            g.drawImage(backgroundImage[5],0,0,null);
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
        MenuOption[] menuOptionsStartmenu;
        MenuOption[] menuOptionsOptionsMenu;
        MenuOption[] menuOptionsSaveMenu2;
        MenuOption[] menuOptionsSaveMenu1;

        menuOptionsSaveMenu2 = new MenuOption[]{new MenuOption(() -> {
            safeManager.safe();
            menuHandler.setCurrentMenu(mainMenu);
        }, "Speichere", 100, 100), new MenuOption(() -> {
            safeManager.load();
            menuHandler.setCurrentMenu(mainMenu);
        }, "Lade", 100, 250), new MenuOption(() -> {
            menuHandler.setCurrentMenu(mainMenu);
        }, "Zurück", 100, 550)
        };

        menuOptionsSaveMenu1 = new MenuOption[]{new MenuOption(() -> {
            safeManager.setCurrentSaveToUse(0);
            saveMenu.setCurrentOption(0);
            saveMenu.setMenuOptions(menuOptionsSaveMenu2);
        }, "Save 1", 100, 100), new MenuOption(() -> {
            safeManager.setCurrentSaveToUse(1);
            saveMenu.setCurrentOption(0);
            saveMenu.setMenuOptions(menuOptionsSaveMenu2);
        }, "Save 2", 100, 250), new MenuOption(() -> {
            safeManager.setCurrentSaveToUse(2);
            saveMenu.setCurrentOption(0);
            saveMenu.setMenuOptions(menuOptionsSaveMenu2);
        }, "Save 3", 100, 400), new MenuOption(() -> {
            menuHandler.setCurrentMenu(mainMenu);
        }, "Zurück", 100, 550)
        };

        menuOptionsStartmenu = new MenuOption[]{new MenuOption(() -> {
            Game.getGame().getSound().stop();
            Game.getGame().getMenuHandler().setLoadBackground(false);
            setGamestate(Gamestate.INGAME);
        }, "Play", 100, 100), new MenuOption(() -> {
            saveMenu.setCurrentOption(0);
            saveMenu.setMenuOptions(menuOptionsSaveMenu1);
            menuHandler.setCurrentMenu(saveMenu);
        }, "Saves", 100, 250), new MenuOption(() -> {
            menuHandler.setCurrentMenu(optionsMenu);
        }, "Optionen", 100, 400), new MenuOption(() -> {
            try {
                stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }, "Exit", 100, 550)
        };

        //OptionsMenu
        String music;
        if(options.isMusic()) {
            music = "Musik On";
        } else music = "Musik Off";
        String autoSave;
        if(options.isAutoSave()) {
            autoSave = "AutoSave On";
        } else autoSave = "AutoSave Off";
        menuOptionsOptionsMenu = new MenuOption[]{new MenuOption(() -> {

                //Change Options and let music start
                options.setMusic(!options.isMusic());
                if (options.isMusic()) {
                    Game.getGame().getMenuHandler().getCurrentMenu().startMusic();
                } else {
                    Game.getGame().getSound().stop();
                }
                //Safe change in config
                safeManager.saveOptions(options);
                //reset this menu option of options menu
                String changeonOff;
                if(options.isMusic()) {
                    changeonOff = "Musik On";
                } else changeonOff = "Musik Off";
                Game.getGame().getMenuHandler().getCurrentMenu().getMenuOptions()[0].setText(changeonOff);
            }, music, 100, 100), new MenuOption(() -> {

            //Change Options
            options.setAutoSave(!options.isAutoSave());
            //Safe change in config
            safeManager.saveOptions(options);
            //reset this menu option of options menu
            String changeonOff;
            if(options.isAutoSave()) {
                changeonOff = "AutoSave On";
            } else changeonOff = "AutoSave Off";
            Game.getGame().getMenuHandler().getCurrentMenu().getMenuOptions()[1].setText(changeonOff);
            }, autoSave, 100, 250),  new MenuOption(() -> {
                //Sicher Options zu den Files
            safeManager.saveOptions(Game.game.getOptions());
            menuHandler.setCurrentMenu(mainMenu);
            }, "Zurück", 100, 550)
            };
        //SaveMenu

        //set new menuoptions
        mainMenu.setMenuOptions(menuOptionsStartmenu);
        optionsMenu.setMenuOptions(menuOptionsOptionsMenu);
        saveMenu.setMenuOptions(menuOptionsSaveMenu1);

        menuHandler = new MenuHandler(mainMenu);

    }

    //Getter and setter


    public SafeManager getSafeManager() {
        return safeManager;
    }

    public LvLHandler getLvLHandler() {
        return lvLHandler;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public static Gamestate getGamestate() {
        return gamestate;
    }

    public void setGamestate(Gamestate newgamestate) {
        gamestate = newgamestate;
        keyListener.setGamestate(gamestate);
        objectHandler.setLeft(false);
        objectHandler.setRight(false);
        objectHandler.setDown(false);
        objectHandler.setUp(false);
        objectHandler.setSpace(false);
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

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public static Game getGame() {
        return game;
    }
    public void togglePlanet(){
        this.onPlanet = !this.onPlanet;
    }

    public int getLvLInt() {
        return LvLInt;
    }

    public void setLvLInt(int lvLInt) {
        LvLInt = lvLInt;
    }

    public boolean isOnPlanet() {
        return onPlanet;
    }

    public void setOnPlanet(boolean onPlanet) {
        this.onPlanet = onPlanet;
    }

    public boolean isBossLvl() {
        return bossLvl;
    }

    public void setBossLvl(boolean bossLvl) {
        this.bossLvl = bossLvl;
    }
}

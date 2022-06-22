package gameDA.gui.menus;

import gameDA.Game;
import gameDA.config.output.BufferedImageLoader;
import gameDA.config.output.Camera;
import gameDA.gui.Gamestate;
import gameDA.objects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;



public class MenuHandler {
    //Das momentan benutzte Menu
    private Menu currentMenu;

    //Das StartMenu in dem das Spiel gestartet wird und zu dem gegebenenfalls zurückgekehrt werden soll
    private Menu startMenu;

    //Die booleans speichern die korrespondierenden Tasteneingaben zur Verarbeitung.
    private boolean up = false, down = false, enter = false;

    //Speichert das Spiel, um Informationen abzurufen und zu verändern
    private final Game game;

    //Speichert, ob der Hintergrund für die Menus gerendert werden soll.
    private boolean loadBackground = true;

    //Lädt die verschiedenen Hintergründe benötigt für die Menus
    private final BufferedImageLoader loader = new BufferedImageLoader();

    //Die verschiedenen Hintergründe benötigt für die Menus
    //Die Bilder für den animierten MainMenu-Hintergrund
    private final BufferedImage background1 = loader.loadImage("/menu/MainMenueBackground.png");
    private final BufferedImage background2 = loader.loadImage("/menu/MainMenueBackground1.png");
    private final BufferedImage background3 = loader.loadImage("/menu/MainMenueBackground2.png");

    //Der Weltall-Hintergrund für Dialoge im Weltall
    private final BufferedImage backgroundSpace = loader.loadImage("/maps/backgroundTest.png");

    //Der Hintergrund für Dialoge auf dem Planeten
    private final BufferedImage backgroundPlanet = loader.loadImage("/maps/Planet1.png");

    //Die Camera aus game für das rendern der Menus
    private Camera camera;

    //Animation des Hintergrundes im Startmenu
    private final Animation animation;

    //
    private int yy;

    //
    private int xx = Game.SCREEN_WIDTH *4;



    /**
     * Erstellen des MenuHandlers
     * @param startMenu Das Menu in dem gestartet werden soll.
     */
    public MenuHandler(Menu startMenu) {
        //Setzen der Attribute
        this.currentMenu = startMenu;
        this.game = Game.getGame();
        this.startMenu = startMenu;
        //Laden von Ressourcen um animation zu erstellen
        BufferedImage backgroundGuy2 = loader.loadImage("/menu/BackgroundGuy2.png");
        BufferedImage backgroundGuy1 = loader.loadImage("/menu/BackgroundGuy1.png");
        BufferedImage backgroundGuy = loader.loadImage("/menu/BackgroundGuy.png");
        BufferedImage[] backgroundGuyS = {backgroundGuy, backgroundGuy1, backgroundGuy2};
        animation = new Animation(10, backgroundGuyS);
        //Einrichten des Menus
        currentMenu.startMusic();
    }

    /**
     * Aktualisiert das momentan ausgewählte Menu und die Animation des Hintergrundes
     */
    public void update(){
        currentMenu.update(this);
        animation.runAnimation();
    }

    /**
     * Rendert das momentan ausgewählte Menu und dessen Hintergrund
     * @param g Die Graphics mithilfe dessen gerendert werden soll
     */
    public void render(Graphics g){
        //Unterscheidung ob, Hintergrund geladen werden muss oder nicht
        if(loadBackground) {
            //Lade Menu hintergrund
            yy++;
            if (yy >= 2) {
                xx--;
                yy = 0;
            }
            g.drawImage(background1, xx - (4 * Game.SCREEN_WIDTH), 0, null);
            g.drawImage(background2, xx - (3 * Game.SCREEN_WIDTH), 0, null);
            g.drawImage(background3, xx - (2 * Game.SCREEN_WIDTH), 0, null);
            g.drawImage(background1, xx - (Game.SCREEN_WIDTH), 0, null);
            if (xx <= Game.SCREEN_WIDTH) xx = 4 * Game.SCREEN_WIDTH;
            animation.drawAnimation(g, -140, 170, 0);

            //Rendern des Titels: "Cool Game"

            //Wähle die Farbe
            g.setColor(Color.GREEN);
            g.setFont(new Font("Gloucester MT Extra Condensed",Font.BOLD,128));
            g.drawString("Cool Game", Game.SCREEN_WIDTH / 2 - 20, Game.SCREEN_HEIGHT / 2 - 100);
            g.setFont(new Font("Gloucester MT Extra Condensed",Font.BOLD,30));
        } else {
            //Rendere keinen Hintergrund

            //Setze die Camera neu, um die aktuelle Camera zu haben
            camera = game.getCamera();

            //Neusetzen des Ursprunges des Koordinatensystems der Grafik
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.translate(-camera.getX(), -camera.getY());

            //Rendern des Hintergrundes je nach Standpunkt des Spielers im Spiel
            if(Game.getGame().isOnPlanet()) {
                //Zeichne den Planeten Hintergrund, falls der Spieler auf dem Planeten ist
                g.drawImage(backgroundPlanet, 0, 0, null);
            } else {
                //Spieler ist nicht auf dem Planeten
                if(Game.getGame().isBossLvl()) {
                    //Falls er in einem BossLevel ist Zeichne den korrespondierenden Hintergrund des BossLevels
                    g.drawImage(Game.getGame().getLvLHandler().getLvLImage(Game.getGame().getLvLInt()), 0, 0, null);
                } else {
                    //Falls der Spieler nur im "normalen" Weltall ist, zeichne den Hintergrund dafür
                    g.drawImage(backgroundSpace, 0, 0, null);
                }
            }
            game.getObjectHandler().render(g);
            graphics2D.translate(camera.getX(), camera.getY());
            g.setFont(new Font("Gloucester MT Extra Condensed",Font.BOLD,32));

        }
        //Rendere das momentan ausgewählte Menu
        currentMenu.render(g);
    }

    public boolean isLoadBackground() {
        return loadBackground;
    }

    public void setLoadBackground(boolean loadBackground) {
        this.loadBackground = loadBackground;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isEnter() {
        return enter;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    /**
     * Setzt das momentan genutzte Menu neu.
     * @param currentMenu Das neue zu benutzende Menu
     */
    public void setCurrentMenu(Menu currentMenu) {
        if(currentMenu.getBackgroundMusic() != this.currentMenu.getBackgroundMusic()) {
            //Falls die Menus unterschiedliche Hintergrundmusik haben

            //Stoppe die gerade laufende Hintergrundmusik
            Game.getGame().getSound().stop();
            //Ersetze das momentan genutzte Menu
            this.currentMenu = currentMenu;
            //Starte die Musik des neuen Menus
            currentMenu.startMusic();
        } else {
            //Falls die Menus die gleiche Hintergrundmusik haben

            //Ersetze das momentan genutzte Menu
            this.currentMenu = currentMenu;
        }
    }

    /**
     * Öffnet das EscapeMenu, welches das StartMenu ohne Hintergrund ist.
     */
    public void openEscapeMenu() {
        //Schalte das Laden des Hintergrundes aus
        this.loadBackground = false;
        //Stoppe die Musik
        //(Musik muss immer gestoppt werden, da das EscapeMenu nur von während dem Spielen,
        // also während man in keinem Menu ist geöffnet werden kann.)
        Game.getGame().getSound().stop();
        //Ersetze das momentan genutzte Menu
        this.currentMenu = startMenu;
        //Starte die Musik des Menus
        currentMenu.startMusic();
        //Wechsle den Status des Spieles zu INMENU
        game.setGamestate(Gamestate.INMENU);
    }

    /**
     * Öffnet das MainMenu, welches das StartMenu mit Hintergrund ist.
     */
    public void openMainMenu() {
        //Schalte das Laden des Hintergrundes aus
        this.loadBackground = true;
        //(Musik muss immer gestoppt werden, da das MainMenu nur von dem DeathMenu aus
        // geöffnet werden kann und diese unterschiedliche Musik haben)
        Game.getGame().getSound().stop();
        //Ersetze das momentan genutzte Menu
        this.currentMenu = startMenu;
        //Starte die Musik des Menus
        currentMenu.startMusic();
    }
}

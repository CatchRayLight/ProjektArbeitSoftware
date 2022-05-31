package GUI;


import Object.*;
import Object.Objects.SpaceShip;
import config.KeyListener;


import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private final int SCREEN_WIDTH  = 1224;
    private final int SCREEN_HEIGHT = 924;
    private boolean isRunning = false;
    private Thread thread;
    private ObjectHandler objectHandler;


    public Game(){
        new GameWindow(SCREEN_HEIGHT,SCREEN_WIDTH,"Space Plugg",this);
        start();

        objectHandler = new ObjectHandler();
        //new objects
        this.addKeyListener(new KeyListener(objectHandler));
        objectHandler.addObj(new SpaceShip(100,100,ObjectID.Player,objectHandler));
    }

    private void stop() throws InterruptedException {
        isRunning = false;
        thread.join();
    }

    private void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        //auf 64ticks beschränken
        double nsPerTick = 1000000000D / 64;
        //die Ticks & Frames initialisieren
        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames");
                System.out.println(objectHandler.gameObjects.get(0).getVelocityX());
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        objectHandler.tick();
    }

    public void render() {
        //starten bei null
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        //die tatsächlichen Frames sind schon vor dem Anzeigen da, "Preloaded", also bswp. 1ster Frame wird gezeigt
        // 2 andere sind schon in der Warteschlange dahinter zum zeigen
        if(bufferStrategy == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g =  bufferStrategy.getDrawGraphics();
        //----------Ab hier wird auf den Canvas gezeichet
        //background
        g.setColor(Color.white);
        g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
        //objects
        objectHandler.render(g);
        //----------bis hier
        g.dispose();
        bufferStrategy.show();

    }



}

package gameDA.gui;


import gameDA.objects.*;
import gameDA.objects.model.SpaceShip;
import gameDA.config.KeyListener;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private final int SCREEN_WIDTH  = 1216;
    private final int SCREEN_HEIGHT = 928;
    private boolean isRunning = false;
    private Thread thread;
    private final ObjectHandler objectHandler;
    private int tick = 0;
    private int frame = 0;


    public Game(){
        new GameWindow(SCREEN_HEIGHT,SCREEN_WIDTH,"Space Plugg",this);
        start();

        objectHandler = new ObjectHandler();
        this.addKeyListener(new KeyListener(objectHandler));
        //Add new obj
        objectHandler.addObj(new SpaceShip(100,100,ObjectID.PLAYER,objectHandler));
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
                tick = ticks;
                frame = frames;
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
        //background & FPS, Tickrate
        g.setColor(Color.white);
        g.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

        //objects
        objectHandler.render(g);
        //----------bis hier
        g.setColor(Color.black);
        g.setFont(new Font("Courier New", Font.BOLD, 10));
        g.drawString("Frames: " + frame,10,20);
        g.drawString("Ticks: " + tick,10,10);
        g.dispose();
        bufferStrategy.show();

    }



}

package Game;

import Entities.Ball;
import Entities.Entity;
import Entities.EntityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class Game extends Canvas implements Runnable {

    public static final int width = 1280, height = width/16*9;

    private Thread thread;
    private boolean running = false;
    private Display display;

    private MouseManager mouseManager;
    private EntityManager entityManager;
    private KeyManager keyManager;

    private Handler handler;
    private BufferStrategy bs;

    public Button massButton;
    public Button radiusButton;

    public JTextField massValue;

    public Game() {

        display = new Display(width, height, "Game");

        mouseManager = new MouseManager();
        keyManager = new KeyManager();
        entityManager = new EntityManager(handler);

        handler = new Handler(this);

        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addKeyListener(keyManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    private void tick() {
        entityManager.tick();
        if (handler.getKeyManager().isChar() == '`') {
            reset();
            handler.getKeyManager().resetChar();
        }

        if (handler.getKeyManager().isChar() == 'r') {
            randomBallGen();
            handler.getKeyManager().resetChar();
        }

        if (handler.getMouseManager().isLeftPressed() && !handler.getEntityManager().isSelect()) {
            ballGen();
            handler.getMouseManager().clearLeftPressed();
        }
    }

    private void render() {

        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0 , width, height);

        entityManager.render(g);
        if (entityManager.isSelect()) {
            massButton = new Button(handler,20,20,"Mass", handler.getEntityManager().getSelectedBall().getMass());
            radiusButton = new Button(handler,20,60,"Radius", handler.getEntityManager().getSelectedBall().getRadius());
            //button = new Button(handler,20,20,"Density", 0);
            massButton.tick();
            massButton.render(g);
            radiusButton.tick();
            radiusButton.render(g);
        }

        bs.show();
        g.dispose();
    }

    public void ballGen() {
        entityManager.addEntity(new Ball(handler, handler.getMouseManager().getMouseX() - 20, handler.getMouseManager().getMouseY() - 20, 0, 0, 0, 0, 20, 0, false));
    }

    public void randomBallGen() {
        for (int i = 0; i < 330; i++) {
            double x, y, radius, velx, vely, accx, accy, mass;
            Random ran = new Random();
            x = ran.nextInt(width);
            y = ran.nextInt(height);
            radius = ran.nextInt(20);
            velx = ran.nextInt(8) - 4;
            vely = ran.nextInt(8) - 4;
            accx = 0;
            accy = 0;
            mass = ran.nextInt(5000);
            entityManager.addEntity(new Ball(handler, x, y, velx, vely, accx, accy, mass/400, mass, true));
        }
    }

    public void reset() {
        entityManager.clearEntity();
    }

    public static void main(String args[]) {
        Game game = new Game();
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}

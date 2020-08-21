package Game;

import Buttons.Button;
import Buttons.ButtonManager;
import Entities.Ball;
import Entities.EntityManager;

import java.awt.*;
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
    private ButtonManager buttonManager;

    private Handler handler;
    private BufferStrategy bs;

    private boolean spacebarCheck = false;

    public Game() {

        display = new Display(width, height, "Game");

        mouseManager = new MouseManager();
        keyManager = new KeyManager();
        entityManager = new EntityManager(handler);
        buttonManager = new ButtonManager(handler);

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

        if (handler.getKeyManager().isChar() == ' ' && !spacebarCheck) {
            handler.getEntityManager().resetCount();
            handler.getEntityManager().time = true;
            spacebarCheck = true;
            handler.getKeyManager().resetChar();
        }

        if (handler.getKeyManager().isChar() == ' ' && spacebarCheck) {
            handler.getEntityManager().time = false;
            spacebarCheck = false;
            handler.getKeyManager().resetChar();
        }

        if (handler.getMouseManager().isLeftPressed() && !handler.getEntityManager().isSelect()) {
            ballGen();
            handler.getMouseManager().clearLeftPressed();
        }

        if (handler.getMouseManager().isLeftPressed() && handler.getEntityManager().isSelect()) {
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
        for (int i = 0; i < entityManager.selectArray().length; i++) {
            if (entityManager.select[i]) {
                buttonManager.addButton(new Button(handler, 20, 20, "Mass", entityManager.getSelectedBall(i).getMass(), 20));
                buttonManager.addButton(new Button(handler, 20, 60, "Radius", entityManager.getSelectedBall(i).getRadius(), 20));
                buttonManager.addButton(new Button(handler, 20, 100, "Density", entityManager.getSelectedBall(i).getDensity(), 20));
                buttonManager.render(g);
            }
        }

        bs.show();
        g.dispose();
    }

    public void ballGen() {
        entityManager.addEntity(new Ball(handler, handler.getMouseManager().getMouseX() - 20, handler.getMouseManager().getMouseY() - 20, 3, 0, 0, 0, 20, 20, 1,
                false, false, false, true));
    }

    public void randomBallGen() {
        for (int i = 0; i < 100; i++) {
            double x, y, radius, velx, vely, accx, accy, mass, density;
            Random ran = new Random();
            x = ran.nextInt(width);
            y = ran.nextInt(height);
            radius = ran.nextInt(50);
            velx = ran.nextInt(8) - 4;
            vely = ran.nextInt(8) - 4;
            accx = 0;
            accy = 0;
            mass = ran.nextInt(5000);
            density = mass / (Math.PI / Math.pow(radius, 2));
            entityManager.addEntity(new Ball(handler, x, y, velx, vely, accx, accy, radius, mass, density,
                    true, false, false, true));
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

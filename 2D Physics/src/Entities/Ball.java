package Entities;

import Game.Handler;

import java.awt.*;
import java.util.Random;

public class Ball extends Entity {

    private Random ran = new Random();
    private int R, G, B;
    private boolean spacebar = false;

    public Ball(Handler handler, double x, double y, double velx, double vely, double accx, double accy, double radius, double mass, double density,
                boolean gravity, boolean massLocked, boolean radiusLocked, boolean densityLocked) {
        super(handler, x, y, velx, vely, accx, accy, radius, mass, density, gravity, massLocked, radiusLocked, densityLocked);

        R = ran.nextInt(256);
        G = ran.nextInt(256);
        B = ran.nextInt(256);
    }

    public void tick() {
        count++;
        if (slowTime && count == 5) {
            move();
            count = 0;
        }
        else if (!slowTime) {
            move();
        }
        selectObject();
    System.out.println(count);
    }

    public void render(Graphics g) {

        if (handler.getMouseManager().getMouseX() >= x &&
            handler.getMouseManager().getMouseX() <= x + radius*2 &&
            handler.getMouseManager().getMouseY() >= y &&
            handler.getMouseManager().getMouseY() <= y + radius*2 ||
            select) {
            g.setColor(Color.WHITE);
            g.fillOval((int) x - 2, (int) y - 2, (int) radius*2 + 4, (int) radius*2 + 4);
        }

        g.setColor(new Color(R, G, B));
        g.fillOval((int) x, (int) y, (int) radius*2, (int) radius*2);

    }

    public void selectObject() {

        if (handler.getMouseManager().getMouseX() >= x &&
                handler.getMouseManager().getMouseX() <= x + radius*2 &&
                handler.getMouseManager().getMouseY() >= y &&
                handler.getMouseManager().getMouseY() <= y + radius*2 &&
                handler.getMouseManager().rightPressed) {
            select = true;
            handler.getMouseManager().clearRightPressed();
        }

        if (select && ((handler.getMouseManager().isRightPressed()) &&
                (handler.getMouseManager().getMouseX() <= x ||
                        handler.getMouseManager().getMouseX() >= x + radius*2 ||
                        handler.getMouseManager().getMouseY() <= y ||
                        handler.getMouseManager().getMouseY() >= y + radius*2))) {
            select = false;
            handler.getMouseManager().clearRightPressed();
        }
    }
}

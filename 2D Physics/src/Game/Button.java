package Game;

import Entities.Ball;

import java.awt.*;
import java.lang.ref.PhantomReference;
import java.util.DuplicateFormatFlagsException;

public class Button {

    private final String title;
    private int x, y;
    private double value;
    private int buttonHeight = 20;
    private int buttonWidth = 100;
    private Handler handler;
    private double mass, radius;

    public Button(Handler handler, int x, int y, String title, double value) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.title = title;
        this.value = value;
    }

    public void tick() {
        if (handler.getEntityManager().getSelectedBall() != null) {
            mass = handler.getEntityManager().getSelectedBall().getMass();
            radius = handler.getEntityManager().getSelectedBall().getRadius();
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(x, y, buttonWidth, buttonHeight);

        //text
        g.setColor(Color.white);
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.drawString(title, x + buttonWidth + 20, y + buttonHeight);
        g.drawString(String.valueOf(value), x, y + buttonHeight);
    }

}

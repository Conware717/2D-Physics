package TextField;

import Game.Handler;

import java.awt.*;

public class BootlegTextField {

    private Handler handler;
    private double value;
    private int position;

    private int width = 100;
    private int height = 20;

    private Rectangle rect;

    private boolean select = false;
    private String mass = "mass";

    public BootlegTextField(Handler handler, double value, int position) {
        this.handler = handler;
        this.value = value;
        this.position = position;

        rect = new Rectangle(20, 40*position, width, height);
    }

    public void tick() {
        selectBootLegTextField();
        System.out.println(select);
    }

    public void render(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        if (hoverCheck(rect) || select) {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        }
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(value), 20, 40*position);
    }

    public boolean hoverCheck(Rectangle r) {
        if (handler.getMouseManager().getMouseX() >= r.x &&
                handler.getMouseManager().getMouseX() <= r.x + width &&
                handler.getMouseManager().getMouseY() >= r.y - height &&
                handler.getMouseManager().getMouseY() <= r.y) {
            return true;
        }
        return false;
    }

    public boolean selectBootLegTextField() {
        if (hoverCheck(rect) && handler.getMouseManager().leftPressed) {
            select = true;
            handler.getMouseManager().clearLeftPressed();
        }
        if (select && !hoverCheck(rect) && handler.getMouseManager().leftPressed) {
            select = false;
            handler.getMouseManager().clearLeftPressed();
        }
        return select;
    }
}

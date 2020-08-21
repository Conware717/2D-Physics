package Buttons;

import Entities.Ball;
import Game.Handler;

import java.awt.*;
import java.lang.ref.PhantomReference;
import java.util.DuplicateFormatFlagsException;

public class Button{

    private final String title;
    private int x, y;
    private double value;
    private int buttonHeight;
    private Handler handler;
    private boolean select;

    public Button(Handler handler, int x, int y, String title, double value, int buttonHeight) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.title = title;
        this.value = value;
        this.buttonHeight = buttonHeight;
    }

    public void tick() {
        selectObject();
    }

    public void render(Graphics g) {

        //text
        g.setColor(Color.white);
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.drawString(title, x , y + buttonHeight);
        g.drawString(String.valueOf(value), x + 100, y + buttonHeight);
    }

    public void selectObject() {

        /*if (handler.getMouseManager().getMouseX() >= x &&
                handler.getMouseManager().getMouseX() <= x + buttonWidth &&
                handler.getMouseManager().getMouseY() >= y &&
                handler.getMouseManager().getMouseY() <= y + buttonHeight &&
                handler.getMouseManager().isRightPressed()) {
            select = true;
            handler.getMouseManager().clearRightPressed();
        }*/

    }

    public boolean getSelect() {
        return select;
    }

}

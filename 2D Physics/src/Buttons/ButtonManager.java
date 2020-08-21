package Buttons;

import Entities.Entity;
import Game.Handler;

import java.awt.*;
import java.util.ArrayList;

public class ButtonManager {

    private boolean select;
    private Button selectedButton;
    public ArrayList<Button> buttons;
    private Handler handler;

    public ButtonManager(Handler handler) {
        buttons = new ArrayList<>();
        this.handler = handler;
    }

    public void tick() {
        for (int i = 0; i < buttons.size(); i++) {
            Button b = buttons.get(i);
            b.tick();
            if (b.getSelect()) {
                select = true;
                selectedButton = b;
            }
            if (!b.getSelect()) {
                select = false;
                selectedButton = null;
            }
        }
    }

    public void render(Graphics g) {
        for (Button b : buttons) {
            b.render(g);
        }
    }

    public boolean isSelect() {
        return select;
    }

    public void addButton(Button b) {
        buttons.add(b);
    }

    public void clearButton() {
        buttons.clear();
    }

}

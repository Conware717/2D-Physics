package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    char keyChar;

    public KeyManager() {
    }

    public char isChar() {
        return keyChar;
    }

    public void resetChar() {
        keyChar = KeyEvent.VK_EURO_SIGN;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyChar = e.getKeyChar();
    }
}

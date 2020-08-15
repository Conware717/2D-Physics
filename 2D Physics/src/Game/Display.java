package Game;

import javax.swing.*;
import java.awt.*;
import java.lang.invoke.VarHandle;

public class Display extends Canvas {

    private JFrame frame;
    private Canvas canvas;

    public Display(int width, int height, String title) {
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMinimumSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width,height));

        frame.add(canvas);
        frame.pack();

    }

    public JFrame getFrame(){
        return frame;
    }

    public Canvas getCanvas(){
        return canvas;
    }
}

package Tag;

import javax.swing.JFrame;
import java.awt.*;

public class Frame extends JFrame {
    public int framex, framey;

    public Frame() {
        framex = 600;
        framey = 600;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ZOMBIE TYCOON");
        setSize(framex, framey);
        setResizable(false);
        init();
    }

    public void init() {
        setLayout(new GridLayout(1, 1, 0, 0));
        Screen s = new Screen(framex, framey);
        add(s);
        setVisible(true);
    }

    public static void main(String[] args) {
        Frame f = new Frame();
    }

    public int getX() {
        return framex;
    }

    public int getY() {
        return framey;
    }
}

package Tag;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Player extends Rectangle {
    public int dx, dy;
    public int health;
    public Rectangle rect;
    public int x, y, width, height;
    public int score = 10000;
    Image player_up_img;
    Image player_down_img;
    Image player_right_img;
    Image player_left_img;

    Screen screen1;

    ArrayList<Image> player_up = new ArrayList<Image>();
    ArrayList<Image> player_down = new ArrayList<Image>();
    ArrayList<Image> player_right = new ArrayList<Image>();
    ArrayList<Image> player_left = new ArrayList<Image>();

    boolean up = true;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean pressed = false;

    int reset_count = 0;
    int press_count = 0;
    int index = 0;

    /// shit i should probably move somwhere else
    int multiplier = 1;
    int damage = 1;
    int speed = 5;

    public Player(int x, int y, int width, int height, Screen screen) {
        // I switched the right and left animations bc shit was acting up
        screen1 = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBounds(x, y, width, height);
        health = 10;
        /// Load Images
        for (int i = 0; i < 6; i++) {
            String pl = String.format("Tag/assets/player%s.png", i);
            String pld = String.format("Tag/assets/pdown%s.png", i);
            String plr = String.format("Tag/assets/pright%s.png", i);
            String pll = String.format("Tag/assets/pleft%s.png", i);

            ImageIcon player_up_img1 = new ImageIcon(pl);
            ImageIcon player_down_img1 = new ImageIcon(pld);
            ImageIcon player_right_img1 = new ImageIcon(pll);
            ImageIcon player_left_img1 = new ImageIcon(plr);

            player_up_img = player_up_img1.getImage();
            player_down_img = player_down_img1.getImage();
            player_right_img = player_right_img1.getImage();
            player_left_img = player_left_img1.getImage();

            player_up.add(player_up_img);
            player_down.add(player_down_img);
            player_right.add(player_right_img);
            player_left.add(player_left_img);
        }
    }

    public void tick() {
        this.x += dx;
        this.y += dy;
        rect = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if (pressed == true) {
            reset_count += 1;
        }
        if (reset_count == 5) {
            press_count += 1;
            reset_count = 0;
        }

        if (press_count >= player_up.size()) {
            press_count = 0;
        }

        if (up == true) {
            g.drawImage(player_up.get(press_count), x, y, screen1);
        }
        if (down == true) {
            g.drawImage(player_down.get(press_count), x, y, screen1);
        }
        if (right == true) {
            g.drawImage(player_right.get(press_count), x, y, screen1);
        }
        if (left == true) {
            g.drawImage(player_left.get(press_count), x, y, screen1);
        }
    }

    public void draw_health(Graphics g) {
        int hx = this.x - 100, hy = this.y + 240;
        for (int i = 0; i < health; i++) {
            g.setColor(Color.red);
            g.fillRect(hx + (i * 15), hy, 15, 15);
        }
    }

    public void collidex() {
        this.x -= this.dx;
    }

    public void collidey() {
        this.y -= this.dy;
    }

    public int getHealth() {
        return health;
    }

}

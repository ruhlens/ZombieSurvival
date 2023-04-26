package Tag;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Hostile extends Rectangle {
    public int dx = 0, dy = 0;
    public int dist_x, dist_y;
    public int dist_x1, dist_y1;
    public boolean collidex = false, collidey = false;
    public boolean collidex1 = false, collidey1 = false;
    int move_count = 0;
    int animation_count = 0;
    public int health = 5;

    Image zombUP;
    Image zombRIGHT;
    Image zombLEFT;
    Image zombDOWN;

    public Screen screen1;

    ArrayList<Image> zomb_up = new ArrayList<Image>();
    ArrayList<Image> zomb_down = new ArrayList<Image>();
    ArrayList<Image> zomb_right = new ArrayList<Image>();
    ArrayList<Image> zomb_left = new ArrayList<Image>();

    public Hostile(int x, int y, int width, int height, Screen screen) {
        setBounds(x, y, width, height);
        Screen screen1 = screen;
        // Load Images
        for (int i = 0; i < 6; i++) {
            String eU = String.format("Tag/assets/zombie%s.png", i);
            String eR = String.format("Tag/assets/zomb_right%s.png", i);
            String eL = String.format("Tag/assets/zomb_left%s.png", i);
            String eB = String.format("Tag/assets/zomb_back%s.png", i);

            ImageIcon zombie_up_img1 = new ImageIcon(eU);
            ImageIcon zombie_right_img1 = new ImageIcon(eR);
            ImageIcon zombie_left_img1 = new ImageIcon(eL);
            ImageIcon zombie_back_img1 = new ImageIcon(eB);

            zombUP = zombie_up_img1.getImage();
            zombRIGHT = zombie_right_img1.getImage();
            zombLEFT = zombie_left_img1.getImage();
            zombDOWN = zombie_back_img1.getImage();

            zomb_up.add(zombUP);
            zomb_right.add(zombRIGHT);
            zomb_left.add(zombLEFT);
            zomb_down.add(zombDOWN);

        }
    }

    public void update() {
        if (this.collidex1 == false && this.collidex == false) {
            this.x += this.dx;
        } else if (this.collidex1 == true || this.collidex == true) {
            this.collidex1 = false;
            this.collidex = false;
            this.collidey = false;
            this.collidey1 = false;
        }

        if (this.collidey1 == false && this.collidey == false) {
            this.y += this.dy;
        } else if (this.collidey1 == true || this.collidey == true) {
            this.collidey1 = false;
            this.collidey = false;
            this.collidex = false;
            this.collidex1 = false;
        }
    }

    public void draw(Graphics g) {
        if (this.dx != 0 || this.dy != 0) {
            animation_count += 1;
        }
        if (animation_count >= 5) {
            move_count += 1;
            animation_count = 0;
        }
        if (move_count >= zomb_up.size()) {
            move_count = 0;
        }
        if (direction() == 1) {
            g.drawImage(zomb_up.get(move_count), x, y, screen1);
        } else if (direction() == -1) {
            g.drawImage(zomb_down.get(move_count), x, y, screen1);
        } else if (direction() == 2) {
            g.drawImage(zomb_right.get(move_count), x, y, screen1);
        } else if (direction() == -2) {
            g.drawImage(zomb_left.get(move_count), x, y, screen1);
        }
    }

    public void draw_health(Graphics g) {
        int hx = this.x + 10, hy = this.y + 50;
        for (int i = 0; i < health; i++) {
            g.setColor(Color.red);
            g.fillRect(hx + (i * 5), hy, 5, 5);
        }
    }

    public void move(Player p) {
        dist_x = p.x - this.x;
        dist_y = p.y - this.y;
        int speed = 3;

        if (this.collidex == false) {
            this.dx = dist_x;
        } else if (this.collidex == true) {
            this.dx = -this.dist_x1;
        }

        if (this.collidey == false) {
            this.dy = dist_y;
        } else if (this.collidey == true) {
            this.dy -= this.dist_y1;
        }

        if (this.collidex1 == true) {
            this.dx = -this.dx;
        }
        if (this.collidey1 == true) {
            this.dy = -this.dy;
        }

        if (this.dx != 0) {
            if (this.dx > speed) {
                this.dx = speed;
            }
            if (this.dx < speed) {
                this.dx = -speed;
            }
        }
        if (this.dy != 0) {
            if (this.dy > speed) {
                this.dy = speed;
            }
            if (this.dy < speed) {
                this.dy = -speed;
            }
        }
    }

    public int direction() {
        int result = 0;
        if (this.dx > 0) {
            result = 2;
        } else if (this.dx < 0) {
            result = -2;
        }
        if (this.dy > 0) {
            result = -1;
        } else if (this.dy < 0) {
            result = 1;
        }

        return result;
    }

    public void crowd_control(ArrayList<Hostile> e) {
        for (int i = 0; i < e.size(); i++) {
            if (this != e.get(i)) {
                if (this.intersects(e.get(i).x + e.get(i).dx, e.get(i).y, 10, 10)) {
                    this.collidex = true;
                    this.dist_x1 = e.get(i).x - this.x;
                }
                if (this.intersects(e.get(i).x, e.get(i).y + e.get(i).dy, 10, 10)) {
                    this.collidey = true;
                    this.dist_y1 = e.get(i).y - this.y;
                }

            }
        }
    }

}

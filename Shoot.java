package Tag;

import java.awt.*;

public class Shoot extends Rectangle {
    public boolean collide = false;
    public int bullet_speed = 20;

    public Shoot(int x, int y) {
        if (this.collide == false) {
            setBounds(x, y, 5, 5);
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, 5, 5);
    }

    public void shoot_up() {
        this.y -= bullet_speed;
    }

    public void shoot_down() {
        this.y += bullet_speed;
    }

    public void shoot_left() {
        this.x -= bullet_speed;
    }

    public void shoot_right() {
        this.x += bullet_speed;
    }
}

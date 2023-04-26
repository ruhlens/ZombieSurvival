package Tag;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Turret {
    int x;
    int y;
    int width;
    int height;
    ArrayList<Hostile> enemies = new ArrayList<Hostile>();
    ArrayList<Shoot> ammo = new ArrayList<Shoot>();

    public Turret(int x, int y, int width, int height, ArrayList<Hostile> badguys) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        enemies = badguys;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
        g.drawRect(x, y, width, height);
        for (int u = 0; u < ammo.size(); u++) {
            ammo.get(u).shoot_down();
            ammo.get(u).draw(g);
        }
    }

    public void detect() {
        Rectangle current = new Rectangle(x, y, 100, 100);
        for (int i = 0; i < enemies.size() - 1; i++) {
            if (enemies.get(i).intersects(current.x - 200, current.y - 200, 800, 800)) {
                Shoot bullet = new Shoot(x, y);
                ammo.add(bullet);
            }
        }

    }

    public ArrayList<Shoot> getAmmoList() {
        return ammo;
    }
}

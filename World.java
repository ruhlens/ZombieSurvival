package Tag;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Random;

/*
 Map Generating Class
 --> 0 = Black
 --> 1 = Yellow
 */
public class World {
    public ArrayList<ArrayList<Integer>> tile_set = new ArrayList<ArrayList<Integer>>();
    public int t_size;
    public ArrayList<Hostile> elist;
    public Player user;
    public ArrayList<Shoot> bulletlist;
    public int hostile_spawn_x, hostile_spawn_y;

    public World(int[][] map, int tile_size, ArrayList<Hostile> enemies, Player p, ArrayList<Shoot> bullets) {
        t_size = tile_size;
        elist = enemies;
        user = p;
        bulletlist = bullets;
        int row_count = 0;
        for (int i = 0; i < map.length; i++) {
            int col_count = 0;
            for (int u = 0; u < map[i].length; u++) {
                ArrayList<Integer> tile = new ArrayList<Integer>();
                int x = tile_size * col_count;
                int y = tile_size * row_count;
                tile.add(x);
                tile.add(y);
                tile.add(map[i][u]);
                tile_set.add(tile);
                col_count += 1;
            }
            row_count += 1;
        }

    }

    public void draw(Graphics g) {
        for (int i = 0; i < tile_set.size(); i++) {
            int type = (int) tile_set.get(i).get(2);
            if (type == 1) {
                g.setColor(Color.gray);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                for (int u = 0; u < elist.size(); u++) {
                    if (current.intersects(elist.get(u).x + elist.get(u).dx, elist.get(u).y, elist.get(u).width,
                            elist.get(u).height)) {
                        elist.get(u).collidex1 = true;
                    }
                    if (current.intersects(elist.get(u).x, elist.get(u).y + elist.get(u).dy, elist.get(u).width,
                            elist.get(u).height)) {
                        elist.get(u).collidey1 = true;
                    }
                }
                if (current.intersects(user.x + user.dx, user.y, user.width, user.height)) {
                    user.collidex();
                }
                if (current.intersects(user.x, user.y + user.dy, user.width, user.height)) {
                    user.collidey();
                }
                for (int m = 0; m < bulletlist.size(); m++) {
                    if (current.intersects(bulletlist.get(m))) {
                        bulletlist.get(m).collide = true;
                    }
                }

            }
            if (type == 0) {
                g.setColor(Color.lightGray);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
            }
            if (type == 2) {
                g.setColor(Color.black);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                if (current.intersects(user.x + user.dx, user.y, user.width, user.height)) {
                    user.collidex();
                }
                if (current.intersects(user.x, user.y + user.dy, user.width, user.height)) {
                    user.collidey();
                }

                hostile_spawn_x = new Random().nextInt(tile_set.get(i).get(0), tile_set.get(i).get(0) + t_size);
                hostile_spawn_y = new Random().nextInt(tile_set.get(i).get(1), tile_set.get(i).get(1) + t_size);
            }
            if (type == 3) {
                g.setColor(Color.cyan);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                g.setColor(Color.black);
                g.drawString("Health", tile_set.get(i).get(0), tile_set.get(i).get(1) + 20);
                g.drawString("Station", tile_set.get(i).get(0), tile_set.get(i).get(1) + 35);
                if (current.intersects(user.x, user.y, user.width, user.height)) {
                    if (user.health < 10) {
                        if (user.score >= 10 - user.health) {
                            user.health += 1;
                            user.score -= 1;
                        }
                    }
                }
            }
            if (type == 4) {
                int price = 1000;
                g.setColor(Color.ORANGE);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                g.setColor(Color.black);
                g.drawString("Double", tile_set.get(i).get(0), tile_set.get(i).get(1) + 20);
                g.drawString("Points", tile_set.get(i).get(0), tile_set.get(i).get(1) + 35);
                g.drawString(Integer.toString(price), tile_set.get(i).get(0), tile_set.get(i).get(1) + 50);
                if (current.intersects(user.x, user.y, user.width, user.height)) {
                    if (user.score >= price) {
                        user.multiplier = 2;
                        user.score -= price;
                    }
                }
            }
            if (type == 5) {
                int price = 300;
                g.setColor(Color.blue);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                g.setColor(Color.black);
                g.drawString("Damage", tile_set.get(i).get(0), tile_set.get(i).get(1) + 20);
                g.drawString("Upgrade", tile_set.get(i).get(0), tile_set.get(i).get(1) + 35);
                g.drawString(Integer.toString(price), tile_set.get(i).get(0), tile_set.get(i).get(1) + 50);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                if (current.intersects(user.x, user.y, user.width, user.height)) {
                    if (user.score >= price) {
                        user.damage = 2;
                        user.score -= price;
                    }
                }
            }
            if (type == 6) {
                int price = 300;
                g.setColor(Color.magenta);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                g.setColor(Color.black);
                g.drawString("Speed", tile_set.get(i).get(0), tile_set.get(i).get(1) + 20);
                g.drawString("Upgrade", tile_set.get(i).get(0), tile_set.get(i).get(1) + 35);
                g.drawString(Integer.toString(price), tile_set.get(i).get(0), tile_set.get(i).get(1) + 50);
                if (current.intersects(user.x, user.y, user.width, user.height)) {
                    if (user.score >= price) {
                        user.speed = 10;
                        user.score -= price;
                    }
                }
            }
            if (type == 7) {
                int price = 100;
                g.setColor(Color.WHITE);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Rectangle current = new Rectangle(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                if (current.intersects(user.x + user.dx, user.y, user.width, user.height)) {
                    user.collidex();
                    if (user.score >= price) {
                        tile_set.get(i).set(2, 0);
                        user.score -= price;
                    }
                }
                if (current.intersects(user.x, user.y + user.dy, user.width, user.height)) {
                    user.collidey();
                    if (user.score >= price) {
                        tile_set.get(i).set(2, 0);
                        user.score -= price;
                    }
                }
            }
            if (type == 8) {
                // g.setColor(Color.blue);
                g.fillRect(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size);
                Turret gun = new Turret(tile_set.get(i).get(0), tile_set.get(i).get(1), t_size, t_size, elist);
                gun.detect();
                gun.draw(g);
                for (int x = 0; x <= gun.ammo.size() - 1; x++) {
                    gun.ammo.get(x).draw(g);
                    gun.ammo.get(x).shoot_down();
                    // System.out.print(x);
                }
                // System.out.print(gun.ammo.size());
                // System.out.print(gun.ammo);
            }
            /// make auto turret upgrade that user can place anywhere
            ////
        }
    }

    public int get_spawnX() {
        return hostile_spawn_x;
    }

    public int get_spawnY() {
        return hostile_spawn_y;
    }
}

package Tag;

//check youtube for culling
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.Font;

public class Screen extends JPanel implements ActionListener, KeyListener {
    Timer t = new Timer(20, this);
    Player p = new Player(300, 300, 50, 50, this);
    ArrayList<Hostile> enemies = new ArrayList<Hostile>();
    ArrayList<Shoot> bullets_up = new ArrayList<Shoot>();
    ArrayList<Shoot> bullets_down = new ArrayList<Shoot>();
    ArrayList<Shoot> bullets_right = new ArrayList<Shoot>();
    ArrayList<Shoot> bullets_left = new ArrayList<Shoot>();
    ArrayList<Shoot> bullets_total = new ArrayList<Shoot>();
    public int offX, offY;
    public int speed;
    public int framex, framey;
    int damage_count = 0;
    // public int score = 0;
    boolean pause = false;
    Menu menu = new Menu(t, p);

    int[][] set = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 7, 0, 0, 0, 0, 0, 0, 3, 0, 1 },
            { 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 8, 0, 0, 0, 0, 0, 0, 0, 5, 1 },
            { 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
    World stage = new World(set, 60, enemies, p, bullets_total);

    public Screen(int fx, int fy) {
        framex = fx;
        framey = fy;
        addKeyListener(this);
        setFocusable(true);
        t.start();
    }

    public void actionPerformed(ActionEvent arg0) {
        check_stat();
        check_live();
        p.tick();
        repaint();
    }

    public void paint(Graphics g) {
        offX = (framex / 2) - p.x;
        offY = (framey / 2) - p.y;
        g.translate(offX, offY);
        g.clearRect(0, 0, getWidth(), getHeight());
        stage.draw(g);
        p.draw_health(g);
        p.draw(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g.drawString(Integer.toString(p.score), p.x - 150, p.y + 255);

        if (pause == true) {
            menu.open_menu(g);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
            enemies.get(i).move(p);
            enemies.get(i).update();
            enemies.get(i).crowd_control(enemies);
            enemies.get(i).draw_health(g);
            if (enemies.get(i).intersects(p.rect)) {
                damage_count += 0;
            }
            if (damage_count >= 10) {
                p.health -= 1;
                damage_count = 0;
            }
        }
        for (int z = 0; z < bullets_down.size(); z++) {
            if (bullets_down.get(z).y > p.y + (framey / 2) || bullets_down.get(z).y < p.y - (framey / 2)
                    || bullets_down.get(z).collide == true) {
                bullets_down.remove(z);
                break;
            }
            bullets_down.get(z).draw(g);
            bullets_down.get(z).shoot_down();
        }
        for (int q = 0; q < bullets_up.size(); q++) {
            if (bullets_up.get(q).y > p.y + (framey / 2) || bullets_up.get(q).y < p.y - (framey / 2)
                    || bullets_up.get(q).collide == true) {
                bullets_up.remove(q);
                break;
            }
            bullets_up.get(q).draw(g);
            bullets_up.get(q).shoot_up();
        }
        for (int u = 0; u < bullets_right.size(); u++) {
            if (bullets_right.get(u).x > p.x + (framex / 2) || bullets_right.get(u).x < p.x - (framex / 2)
                    || bullets_right.get(u).collide == true) {
                bullets_right.remove(u);
                break;
            }
            bullets_right.get(u).draw(g);
            bullets_right.get(u).shoot_right();
        }
        for (int v = 0; v < bullets_left.size(); v++) {
            if (bullets_left.get(v).x > p.x + (framex / 2) || bullets_left.get(v).x < p.x - (framex / 2)
                    || bullets_left.get(v).collide == true) {
                bullets_left.remove(v);
                break;
            }
            bullets_left.get(v).draw(g);
            bullets_left.get(v).shoot_left();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        p.pressed = true;
        speed = p.speed;
        // Player Controls
        if (event.getKeyCode() == KeyEvent.VK_W) {
            p.dy = -speed;
            p.up = true;
            p.down = false;
            p.left = false;
            p.right = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_S) {
            p.dy = speed;
            p.down = true;
            p.up = false;
            p.left = false;
            p.right = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_D) {
            p.dx = speed;
            p.right = true;
            p.down = false;
            p.left = false;
            p.up = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_A) {
            p.dx = -speed;
            p.left = true;
            p.down = false;
            p.up = false;
            p.right = false;
        }

        // Menu Controls
        if (pause == false) {
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                pause = true;
            }
        } else if (pause == true) {
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                pause = false;
                menu.close_menu();
            }
        }
    }

    public void keyReleased(KeyEvent event) {
        // Shoot Controls
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            Shoot bullet = new Shoot(p.x, p.y);
            bullets_up.add(bullet);
            bullets_total.add(bullet);
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            Shoot bullet = new Shoot(p.x, p.y);
            bullets_down.add(bullet);
            bullets_total.add(bullet);
        }
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            Shoot bullet = new Shoot(p.x, p.y);
            bullets_left.add(bullet);
            bullets_total.add(bullet);
        }
        if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            Shoot bullet = new Shoot(p.x, p.y);
            bullets_right.add(bullet);
            bullets_total.add(bullet);
        }

        // Movement Controls pt2
        p.pressed = false;
        if (event.getKeyCode() == KeyEvent.VK_W) {
            // p.up = false;
            if (p.down == false) {
                p.dy = 0;
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_S) {
            // p.down = false;
            if (p.up == false) {
                p.dy = 0;
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_D) {
            // p.right = false;
            if (p.left == false) {
                p.dx = 0;
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_A) {
            // p.left = false;
            if (p.right == false) {
                p.dx = 0;
            }
        }

    }

    public void check_live() {
        // Checks if enemies Hostiles intersect bullet and creates random hostile
        // spawning
        for (int i = 0; i < bullets_total.size(); i++) {
            for (int x = 0; x < enemies.size(); x++) {
                if (bullets_total.get(i).collide == true) {
                    bullets_total.remove(i);
                    break;
                } else if (bullets_total.get(i).intersects(enemies.get(x))) {
                    enemies.get(x).health -= p.damage;
                    p.score += p.multiplier;
                    bullets_total.get(i).collide = true;
                }
                if (enemies.get(x).health <= 0) {
                    enemies.remove(x);
                }
            }
        }
        if (enemies.size() < 10000) {
            int BGx, BGy;
            BGx = stage.get_spawnX();
            BGy = stage.get_spawnY();
            Hostile badGuy = new Hostile(BGx, BGy, 50, 50, this);
            enemies.add(badGuy);
        }
        System.out.println(enemies.size());
    }

    public void keyTyped(KeyEvent arg0) {
        // nothing here
    }

    public int[][] generate_tiles() {
        int[][] tileMap = new int[10][10];
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[0].length; j++) {
                tileMap[i][j] = 0;
            }
        }
        return tileMap;
    }

    public void check_stat() {
        if (p.health <= 0) {
            t.stop();
        }
    }

    public int get_score() {
        return p.score;
    }
}
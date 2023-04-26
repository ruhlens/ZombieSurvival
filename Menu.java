package Tag;

import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class Menu{
    Timer time;
    Player p;
    boolean pause;

    public Menu(Timer t, Player player){
        time = t;
        p = player;
    }

    public void open_menu(Graphics g){
        time.stop();
        pause = true;
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
        g.drawString("Paused", p.x - 25, p.y - 50);
        g.drawString("STORE", p.x - 30, p.y);
    }

    public void close_menu(){
        time.start();
        pause = false;
    }
}

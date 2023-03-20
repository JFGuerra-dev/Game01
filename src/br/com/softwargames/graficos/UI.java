package br.com.softwargames.graficos;

import br.com.softwargames.entities.Player;

import java.awt.*;

public class UI {
    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(8, 4, 50, 8);
        g.setColor(Color.green);
        g.fillRect(8, 4, (int)((Player.life / Player.MAX_LIFE)*50), 8);
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 7));
        g.drawString((int)Player.life + "/" + (int)Player.MAX_LIFE, 9, 10);
    }
}

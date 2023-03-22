package br.com.softwargames.main;

import java.awt.*;

public class Menu {
    public boolean up, down;
    public String[] options = {"Novo jogo", "Carregar jogo", "Sair"};
    public int currentOption = 0;
    public int maxOption = options.length - 1;
    public void tick(){
        if(up){
            up = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOption;
            }
        } else if(down){
            down = false;
            currentOption++;
            if(currentOption > maxOption){
                currentOption = 0;
            }
        }

    }

    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGTH*Game.SCALE);
        g.setColor(Color.yellow);
        g.setFont(new Font("arial", Font.PLAIN, 30));
        g.drawString("--<Zelda Clone>--", ((Game.WIDTH*Game.SCALE) / 2) - 100, ((Game.HEIGTH*Game.SCALE) / 2) - 100);

        //opções
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 15));
        g.drawString("Novo jogo", ((Game.WIDTH*Game.SCALE) / 2) - 20, ((Game.HEIGTH*Game.SCALE) / 2) - 70);
        g.drawString("Carregar jogo", ((Game.WIDTH*Game.SCALE) / 2) - 30, ((Game.HEIGTH*Game.SCALE) / 2) - 40);
        g.drawString("Sair", ((Game.WIDTH*Game.SCALE) / 2) , ((Game.HEIGTH*Game.SCALE) / 2) - 10);

        if(options[currentOption].equals("Novo jogo")){
            g.drawString(">", ((Game.WIDTH*Game.SCALE) / 2) - 70, 250);
        } else if(options[currentOption].equals("Carregar jogo")){
            g.drawString(">", ((Game.WIDTH*Game.SCALE) / 2) - 70, 280);
        } else {
            g.drawString(">", ((Game.WIDTH*Game.SCALE) / 2) - 70, 310);
        }

    }
}

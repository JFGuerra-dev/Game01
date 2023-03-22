package br.com.softwargames.entities;

import br.com.softwargames.main.Game;
import br.com.softwargames.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BulletShoot extends Entity{

    private int directionX;
    private int directionY;
    private final int SPEED = 4;
    private final int MAX_BULLET_LIFE = 25;
    private int bulletFile = 0;

    public BulletShoot(int x, int y, int width, int heigth, BufferedImage sprite, int directionX, int directionY) {
        super(x, y, width, heigth, sprite);
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void tick(){
        x += directionX * SPEED;
        y += directionY * SPEED;

        bulletFile++;
        if(bulletFile == MAX_BULLET_LIFE){
            Game.bullets.remove(this);
        }
    }

    public void render(Graphics g){
        g.setColor(Color.black);
        if(Game.player.left){
            g.fillOval(this.getX() - Camera.x - 9, this.getY() - Camera.y + 3, width, heigth);
        } else {
            g.fillOval(this.getX() - Camera.x + 22, this.getY() - Camera.y + 3, width, heigth);
        }
    }
}

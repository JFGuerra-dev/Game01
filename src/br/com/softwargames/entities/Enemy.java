package br.com.softwargames.entities;

import br.com.softwargames.main.Game;
import br.com.softwargames.world.Camera;
import br.com.softwargames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    private double speed = 0.8;
    private final BufferedImage[] enemy;
    private int frames = 0;
    private final int MAX_FRAMES = 10;
    private int index = 0;
    private final int MAX_INDEX = 1;
    private boolean moved;
    private int maskX = 0;
    private int maskY = 0;
    private int maskWidth = 10;
    private int maskHeigth = 10;
    public Enemy(int x, int y, int width, int heigth, BufferedImage sprite) {
        super(x, y, width, heigth, sprite);

        enemy = new BufferedImage[2];

        for(int i = 0;i < enemy.length;i++){
            enemy[i] = Game.spritesheet.getSprite(65 + (i * 16), 17, 16, 16);
        }

    }

    public void tick(){
        if(!isCollidingWithPlayer()) {
            moved = false;
            if (x < Game.player.getX() && World.placeFree((int) (x + speed), this.getY()) && !isColliding((int) (x + speed), this.getY())) {
                moved = true;
                x += speed;
            } else if ( x > Game.player.getX() && World.placeFree((int) (x - speed), this.getY()) && !isColliding((int) (x - speed), this.getY())) {
                moved = true;
                x -= speed;
            }

            if ( y < Game.player.getY() && World.placeFree(this.getX(), (int) (y + speed)) && !isColliding(this.getX(), (int) (y + speed))) {
                moved = true;
                y += speed;
            } else if ( y > Game.player.getY() && World.placeFree(this.getX(), (int) (y - speed)) && !isColliding(this.getX(), (int) (y - speed))) {
                moved = true;
                y -= speed;
            }
        } else{
            if(Game.rand.nextInt(100) < 10){
                Game.player.life -= Game.rand.nextInt(3);
                Game.player.isDamaged = true;
            }
        }
        if(moved){
            frames++;
            if(frames == MAX_FRAMES){
                frames = 0;
                index++;
                if(index > MAX_INDEX) index = 0;
            }
        }
    }

    public void render(Graphics g){
        super.render(g);
        g.drawImage(enemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
    public boolean isCollidingWithPlayer(){
        Rectangle currentEnemy = new Rectangle(this.getX() + maskX, this.getY() + maskY, maskWidth, maskHeigth);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

        return currentEnemy.intersects(player);
    }

    public boolean isColliding(int xnext, int ynext){
        Rectangle currentEnemy = new Rectangle(xnext + maskX, ynext + maskY, maskWidth, maskHeigth);
        for(int i = 0;i < Game.enemies.size();i++){
            Enemy enemy = Game.enemies.get(i);
            if(enemy == this) continue;
            Rectangle targetEnemy = new Rectangle(enemy.getX() + maskX, enemy.getY() + maskY, maskWidth, maskHeigth);
            if(currentEnemy.intersects(targetEnemy)) return true;
        }
        return false;
    }
}

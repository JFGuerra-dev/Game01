package br.com.softwargames.entities;

import br.com.softwargames.main.Game;
import br.com.softwargames.world.Camera;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Entity {
    public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(65, 0, 16, 16);
    public static BufferedImage WEAPON = Game.spritesheet.getSprite(81, 0, 16, 16);
    public static BufferedImage AMMO = Game.spritesheet.getSprite(97, 0, 16, 16);
    public static BufferedImage ENEMY = Game.spritesheet.getSprite(66, 17, 16, 16);
    public static BufferedImage WEAPON_LEFT = Game.spritesheet.getSprite(129, 0, 16, 16);
    public static BufferedImage WEAPON_RIGHT = Game.spritesheet.getSprite(113, 0, 16, 16);



    protected double x;
    protected double y;
    protected int width;
    protected int heigth;
    private BufferedImage sprite;
    private int maskX;
    private int maskY;
    private int maskWidth;
    private int maskHeigth;

    public Entity(int x, int y, int width, int heigth, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.sprite = sprite;

        this.maskX = 0;
        this.maskY = 0;
        this.maskWidth = width;
        this.maskHeigth = heigth;
    }

    public void setMask(int maskX, int maskY, int maskWidth, int maskHeigth){
        this.maskX = maskX;
        this.maskY = maskY;
        this.maskWidth = maskWidth;
        this.maskHeigth = maskHeigth;
    }

    public int getX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public void render(Graphics g){
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

    public void tick(){
        System.out.println("");

    }

    public static boolean isColliding(Entity entity1, Entity entity2){
        Rectangle e1Mask = new Rectangle(entity1.getX() + entity1.maskX, entity1.getY() + entity1.maskY, entity1.maskWidth, entity1.maskHeigth);
        Rectangle e2Mask = new Rectangle(entity2.getX() + entity2.maskX, entity2.getY() + entity2.maskY, entity2.maskWidth, entity2.maskHeigth);

        return e1Mask.intersects(e2Mask);
    }
}

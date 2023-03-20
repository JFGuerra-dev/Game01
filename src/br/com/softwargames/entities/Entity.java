package br.com.softwargames.entities;

import br.com.softwargames.main.Game;
import br.com.softwargames.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Entity {
    public static BufferedImage LIFEPACK = Game.spritesheet.getSprite(65, 0, 16, 16);
    public static BufferedImage WEAPON = Game.spritesheet.getSprite(81, 0, 16, 16);
    public static BufferedImage AMMO = Game.spritesheet.getSprite(97, 0, 16, 16);
    public static BufferedImage ENEMY = Game.spritesheet.getSprite(66, 17, 16, 16);


    protected double x;
    protected double y;
    protected int width;
    protected int heigth;
    private BufferedImage sprite;

    public Entity(double x, double y, int width, int heigth, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.sprite = sprite;
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
}

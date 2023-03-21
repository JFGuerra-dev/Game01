package br.com.softwargames.entities;

import br.com.softwargames.graficos.Spritesheet;
import br.com.softwargames.main.Game;
import br.com.softwargames.world.Camera;
import br.com.softwargames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import static br.com.softwargames.main.Game.player;

public class Player extends Entity{

    public boolean right, left, up, down;
    public final double SPEED = 1.4;
    private final BufferedImage[] rightPlayer;
    private final BufferedImage[] leftPlayer;
    private BufferedImage centerPlayer;
    private BufferedImage playerDamage;
    private int frames = 0;
    private final int MAX_FRAMES = 10;
    private int index = 0;
    private final int MAX_INDEX = 1;
    private boolean moved;
    public double life = 100;
    public final double MAX_LIFE = 100;
    public static int ammo = 0;
    public static final int MAX_AMMO = 30;
    public boolean isDamaged = false;
    private int damageFrames = 0;
    private boolean hasGun;

    public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
        super(x, y, width, heigth, sprite);
        rightPlayer = new BufferedImage[2];
        leftPlayer = new BufferedImage[2];
        playerDamage = Game.spritesheet.getSprite(0, 17, 16, 16);
        centerPlayer = Game.spritesheet.getSprite(17, 17, 16, 16);

        for(int i = 0;i < rightPlayer.length;i++){
            rightPlayer[i] = Game.spritesheet.getSprite(33 + (i * 16), 0, 16, 16);
        }

        for(int i = 0;i < leftPlayer.length;i++){
            leftPlayer[i] = Game.spritesheet.getSprite(33 + (i * 16), 17, 16, 16);
        }
    }

    @Override
    public void tick(){
        moved = false;
        if(right && World.placeFree((int)(x + SPEED), this.getY())){
            moved = true;
            x += SPEED;
        } else if(left && World.placeFree((int)(x - SPEED), this.getY())){
            moved = true;
            x -= SPEED;
        }

        if(up && World.placeFree(this.getX(), (int)(y - SPEED))){
            moved = true;
            y -= SPEED;
        } else if(down && World.placeFree(this.getX(), (int)(y + SPEED))){
            moved = true;
            y += SPEED;
        }
        if(moved){
            frames++;
            if(frames == MAX_FRAMES){
                frames = 0;
                index++;
                if(index > MAX_INDEX) index = 0;
            }
        }

        this.checkCollisionLifePack();
        this.checkCollisionAmmo();
        this.checkCollisionGun();

        if(isDamaged){
            this.damageFrames++;
            if(this.damageFrames == 5){
                this.damageFrames = 0;
                isDamaged = false;
            }
        }

        if(life <= 0){
            Game.entities.clear();
            Game.enemies.clear();
            Game.entities = new ArrayList<>();
            Game.enemies = new ArrayList<>();
            Game.spritesheet = new Spritesheet("/spritesheet.png");
            player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(33, 0, 16, 16));
            Game.entities.add(Game.player);
            Game.world = new World("/map.png");
            return;
        }

        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGTH/2), 0, World.HEIGTH*16 - Game.HEIGTH);
    }

    public void checkCollisionGun(){
        for(int i = 0;i < Game.entities.size();i++){
            Entity atual = Game.entities.get(i);
            if(atual instanceof Weapon){
                if(Entity.isColliding(this, atual)){
                    hasGun = true;
                    Game.entities.remove(atual);
                }
            }
        }
    }
    public void checkCollisionAmmo(){
        for(int i = 0;i < Game.entities.size();i++){
            Entity atual = Game.entities.get(i);
            if(atual instanceof Ammo){
                if(Entity.isColliding(this, atual)){
                    ammo += 10;
                    if(ammo > 30)
                        ammo = 30;
                    Game.entities.remove(atual);
                    return;
                }
            }
        }
    }

    public void checkCollisionLifePack(){
        for(int i = 0;i < Game.entities.size();i++){
            Entity atual = Game.entities.get(i);
            if(atual instanceof Lifepack){
                if(Entity.isColliding(this, atual)){
                    life += 20;
                    if(life > 100)
                        life = 100;
                    Game.entities.remove(atual);
                    return;
                }
            }
        }
    }

    @Override
    public void render(Graphics g){
        if(!isDamaged) {
            if (right) {
                g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                if(hasGun){
                    g.drawImage(Entity.WEAPON_RIGHT, this.getX() + 10 - Camera.x, this.getY() - Camera.y, null);
                }
            } else if (left) {
                g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
                if(hasGun){
                    g.drawImage(Entity.WEAPON_LEFT, this.getX() - 10 - Camera.x, this.getY() - Camera.y, null);

                }
            } else {
                g.drawImage(centerPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
                if(hasGun){
                    g.drawImage(Entity.WEAPON_RIGHT, this.getX() + 10 - Camera.x, this.getY() - Camera.y, null);
                }
            }
        } else {
            g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }
}

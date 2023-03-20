package br.com.softwargames.entities;

import br.com.softwargames.main.Game;
import br.com.softwargames.world.Camera;
import br.com.softwargames.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Player extends Entity{

    public boolean right, left, up, down;
    public final double SPEED = 1.4;
    private final BufferedImage[] rightPlayer;
    private final BufferedImage[] leftPlayer;
    private BufferedImage centerPlayer;
    private int frames = 0;
    private final int MAX_FRAMES = 10;
    private int index = 0;
    private final int MAX_INDEX = 1;
    private boolean moved;
    public static double life = 100;
    public static final double MAX_LIFE = 100;
    public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
        super(x, y, width, heigth, sprite);
        rightPlayer = new BufferedImage[2];
        leftPlayer = new BufferedImage[2];
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

        this.checkItems();

        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGTH/2), 0, World.HEIGTH*16 - Game.HEIGTH);
    }

    public void checkItems(){

        Iterator<Entity> itr = Game.entities.iterator();
        while(itr.hasNext()){
            Entity currentEntity = itr.next();
            if(currentEntity instanceof Lifepack){
                if(Entity.isColliding(this, currentEntity)){
                    life += 8;
                    if(life > 100) life = 100;
                    itr.remove();
                    return;
                }
            }
        }


//        for(int i = 0;i < Game.entities.size();i++){
//            Entity atual = Game.entities.get(i);
//            if(atual instanceof Lifepack){
//                if(Entity.isColliding(this, atual)){
//                    life += 8;
//                    if(life > 100)
//                        life = 100;
//                    Game.entities.remove(atual);
//                    return;
//                }
//            }
//        }
    }

    @Override
    public void render(Graphics g){
        if(right){
            g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else if(left){
            g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else {
            g.drawImage(centerPlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }
}

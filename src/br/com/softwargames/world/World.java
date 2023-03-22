package br.com.softwargames.world;

import br.com.softwargames.entities.*;
import br.com.softwargames.graficos.Spritesheet;
import br.com.softwargames.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static br.com.softwargames.main.Game.player;

public class World {
    public static Tile[] tiles;
    public static int WIDTH;
    public static int HEIGTH;
    public static final int TILE_SIZE = 16;
    public World(String path){
        try {
            BufferedImage map = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGTH = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for(int xx = 0;xx < map.getWidth();xx++){
                for(int yy = 0;yy < map.getHeight();yy++){
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    //chao
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
                    if(pixelAtual == 0xFFFFFFFF){
                        //Parede
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
                    } else if(pixelAtual == 0xFF0026FF){
                        //Player
                        Game.player.setX(xx*16);
                        Game.player.setY(yy*16);
                    } else if(pixelAtual == 0xFFFF0000){
                        //Enemy
                        Enemy enemy = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY);
                        Game.entities.add(enemy);
                        Game.enemies.add(enemy);
                    } else if(pixelAtual == 0xFFFF00DC){
                        //Lifepack
                        Lifepack lifepack = new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK);
                        Game.entities.add(lifepack);
                    } else if(pixelAtual == 0xFFFFD800){
                        //Ammo
                        Ammo ammo = new Ammo(xx*16, yy*16, 16, 16, Entity.AMMO);
                        Game.entities.add(ammo);

                    } else if(pixelAtual == 0xFF808080){
                        //Weapon
                        Weapon weapon = new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON);
                        Game.entities.add(weapon);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(Graphics g){
        int xstart = Camera.x >> 4;
        int ystart = Camera.y >> 4;

        int xfinal = xstart + (Game.WIDTH >> 4);
        int yfinal = ystart + (Game.HEIGTH >> 4);

        for(int xx = xstart;xx <= xfinal;xx++){
            for(int yy = ystart;yy <= yfinal;yy++){
                if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGTH){
                    continue;
                }
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }

    public static boolean placeFree(int xnext, int ynext) {
        int xTile1 = xnext / TILE_SIZE;
        int yTile1 = ynext / TILE_SIZE;

        int xTile2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int yTile2 = ynext / TILE_SIZE;

        int xTile3 = xnext / TILE_SIZE;
        int yTile3 = (ynext + TILE_SIZE - 1)/ TILE_SIZE;

        int xTile4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int yTile4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[xTile1 + (yTile1 * World.WIDTH)] instanceof WallTile)
                || (tiles[xTile2 + (yTile2 * World.WIDTH)] instanceof WallTile)
                || (tiles[xTile3 + (yTile3 * World.WIDTH)] instanceof WallTile)
                || (tiles[xTile4 + (yTile4 * World.WIDTH)] instanceof WallTile));

    }

    public static void restartWorld(String level){
        Game.entities.clear();
        Game.enemies.clear();
        Game.entities = new ArrayList<>();
        Game.enemies = new ArrayList<>();
        Game.spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(33, 0, 16, 16));
        Game.entities.add(Game.player);
        Game.world = new World("/"+level);
        return;
    }
}

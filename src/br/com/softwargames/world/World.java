package br.com.softwargames.world;

import br.com.softwargames.entities.*;
import br.com.softwargames.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class World {
    private Tile[] tiles;
    public static int WIDTH;
    public static int HEIGTH;
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
                        Game.entities.add(new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY));
                    } else if(pixelAtual == 0xFFFF00DC){
                        //Lifepack
                        Game.entities.add(new Lifepack(xx*16, yy*16, 16, 16, Entity.LIFEPACK));
                    } else if(pixelAtual == 0xFFFFD800){
                        //Ammo
                        Game.entities.add(new Ammo(xx*16, yy*16, 16, 16, Entity.AMMO));

                    } else if(pixelAtual == 0xFF808080){
                        //Weapon
                        Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON));
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
}

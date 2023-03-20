package br.com.softwargames.graficos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    public BufferedImage spritesheet;


    public Spritesheet(String path){
        try {
            spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Retorna uma parte do spritesheet de acordo com as coordenadas passadas como parametro
    public BufferedImage getSprite(int x, int y, int width, int heigth){
        return spritesheet.getSubimage(x, y, width, heigth);
    }
}

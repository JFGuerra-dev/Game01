package br.com.softwargames.main;

import br.com.softwargames.entities.Enemy;
import br.com.softwargames.entities.Entity;
import br.com.softwargames.entities.Player;
import br.com.softwargames.graficos.Spritesheet;
import br.com.softwargames.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {

    @Serial
    private static final long serialVersionUID = 1L;
    private boolean isRunning;
    private static JFrame frame;
    public static final int WIDTH = 240;
    public static final int HEIGTH = 160;
    private static final int SCALE = 4;
    private static final double SECOND_AS_NS = 1000000000; //Um segundo no formato de nano segundos
    private static final double SECOND = 1000; //Um segundo no formato de milli segundos
    private static final double FPS = 60.0; //Taxa de atualização dos frames por segundo
    private BufferedImage image;
    private Thread thread;
    public static Spritesheet spritesheet;
    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static Player player;
    public static World world;

    public static Random rand;
    public Game(){
        rand = new Random();
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGTH*SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_RGB);
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(33, 0, 16, 16));
        world = new World("/map.png");
        entities.add(player);
    }

    public void initFrame(){
        frame = new JFrame("Game 01");
        frame.add(this); //Adicionar o escopo do objeto atual ao contexto do frame
        frame.setResizable(false); //Impedir que o usuário redimencione a resolução
        frame.pack(); //Metodo para calcular dimensões
        frame.setLocationRelativeTo(null); //Iniciar a janela no centro
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Propriedade para fechar em definitivo o game ao clicar para fechar a janela
        frame.setVisible(true); //Tornar a tela visível
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        for(Entity entity : entities) entity.tick();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
        } else {
            Graphics g = image.getGraphics(); //Classe que possibilita inserir elementos na tela
            //Definir background da tela
            g.setColor(new Color(0, 0, 0)); //Inserir cor
            g.fillRect(0, 0, WIDTH, HEIGTH);//Desenhar retangulo na tela com a cor definida na linha acima

            //Renderização do game
            world.render(g);
            for(Entity entity : entities) entity.render(g);

            g.dispose();
            g = bs.getDrawGraphics();
            g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGTH*SCALE, null); //Desenhar na tela o que foi manipulado
            bs.show(); //Mostrar gráficos na tela

        }

    }

    //Game Loop responsável por manter a instância do jogo rodando
    @Override
    public void run() {
        long lastTime = System.nanoTime(); // Tempo atual da máquina em nano segundos
        double nanoSeconds = SECOND_AS_NS / FPS;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSeconds;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= SECOND){
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer += SECOND;
            }
        }

        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
    }
}


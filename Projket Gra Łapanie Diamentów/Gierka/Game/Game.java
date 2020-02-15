package Game;

import java.io.IOException;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Game odpowada za tworzenie logike gry i rozporadzanie
 * wszytkimi informacjami na temat gracza przeciwnika i jedzenia
 */
public class Game extends Thread{

    public GameWindow window;
    public int width,height;
    public Player player;
    public static Client t;
    private Timer timer;
    private KeyController keyController;
    public List<Player> enemyPlayer = new ArrayList<>();

    BufferStrategy bs;
    Graphics g;


    /**
     * @param width szerokosc odswiezania okna
     * @param height wysokosc odswiezania okna
     * @param window umozlwia dostep do okna gry
     * @throws IOException
     */
    public Game(int width, int height, GameWindow window)throws IOException {
        if(window==null)
        {
            throw new IOException("window is null");
        }
        this.width = width;
        this.height = height;
        this.window = window;

    }

    /**
     * initializuje gracza i polaczenia z klawiatura oraz wyswietlanym oknem gry
     */
    private void initialize(){
        player = new Player(0, 450,550, 0, 0,0, t);
        keyController = new KeyController(player,window,t);
        window.getFrame().addKeyListener(keyController);
        window.input.addKeyListener(keyController);

        t.start();
    }


    /**
     * odpowiada za czestotliwosc odswiezania obiektow w oknie gry
     */
    @Override
    public void run() {
        initialize();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.updatePositions();
                renderGame();
            }
        },0,20); //10
    }


    /**
     * odpowiada za za reder planszy z gra oraz logike zbierania punktów
     */
    private void renderGame(){
        bs = window.getCanvas().getBufferStrategy();
        if(bs == null) {
            window.getCanvas().createBufferStrategy(4);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0,0,width,height);

        g.setColor(Color.GRAY);
        g.drawRect(50,-1,900,700);


        if( player.foodShow==1)
        {
            for (int i = 0; i <= 5; i++)
            {
                player.foodStatus[i] = player.foodStatus[i] +2;
                player.foodY[i] = player.foodY[i] + 2;

                if(player.foodY[i]>650) {

                    player.foodRender[i]=1;

                    player.foodY[i]=-150;
                    player.foodX[i] = player.foodX[i]+30;
                    if(player.foodX[i]>900)
                    {
                        player.foodX[i]=player.foodX[i]%900;
                    }

                    if(player.foodX[i]<100)
                    {
                        player.foodX[i]=player.foodX[i] + 100;
                    }

                    player.foodColor[i] = player.foodColor[i]+1;
                    if(player.foodColor[i] == 4)
                    {
                        player.foodColor[i] = 1;
                    }
                }

                Rectangle r1 = new Rectangle( player.x,  player.y, 100, 100);
                Rectangle r2 = new Rectangle(player.foodX[i], player.foodY[i], 50, 50);
                Rectangle r3 = r1.intersection(r2);


                if (player.foodRender[i]==1 && r3.width > 49 && r3.height > 49)
                {
                    if(player.foodColor[i] == player.colorNumber) {
                        player.scoreStatus = player.scoreStatus + 100;
                    }
                    else
                        {
                           player.scoreStatus = player.scoreStatus -50;
                    }

                    player.foodRender[i]=0;
                    player.client.updateFood();
                }
            }
        }

        player.renderFood(g);
        player.renderPlayer(g);
        for(Player enemy : enemyPlayer){
            enemy.renderPlayer(g);
        }


        g.setColor(Color.BLUE);
        g.fillRect(50,650,900,50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Lucida Sans", Font.PLAIN, 30));
        g.drawString("SCORE: " +player.scoreStatus , 200, 685);

        if(player.scoreStatus>=10000) {
            g.setFont(new Font("Lucida Sans", Font.PLAIN, 50));
            g.setColor(Color.RED);
            g.drawString("YOU WIN" , 400, 300);
            player.foodShow=0;
        }

        if(enemyPlayer.size()==0)
        {
            g.drawString("Waiting for other player...", 500, 685);
        }
        for(Player enemy : enemyPlayer){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lucida Sans", Font.PLAIN, 30));
            g.drawString("ENEMY SCORE: " +enemy.scoreStatus , 500, 685);
            if(enemy.scoreStatus>=10000) {
                g.setFont(new Font("Lucida Sans", Font.PLAIN, 50));
                g.setColor(Color.RED);
                g.drawString("YOU LOSE" , 400, 300);
                player.foodShow=0;
            }
        }

        bs.show();
        g.dispose();
    }
}

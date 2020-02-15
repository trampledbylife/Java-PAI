package Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


/**
 * GameWindow odpowiada za wyswietlanie okna gry
 */
public class GameWindow {
    private JFrame frame;
    public Canvas canvas;
    public JTextField input;
    public JTextArea showText;
    public Client client;

    private int width,height;

    /**
     * @param width  szerokosc okna
     * @param height wysokosc okna
     * @param client umozliwaia komunikacje z serverem
     */
    public GameWindow(int width, int height, Client client) throws IOException {
        this.width = width;
        this.height = height;
        this.client = client;
        initialize();
    }

    /**
     * @return zwraca obiekt na ktorym mozna renderowac grafike
     */
    public Canvas getCanvas()
    {
        return canvas;
    }

    /**
     * @return zwraca obiekt umozliwajacy dodawanie elementow do okna gry
     */
    public JFrame getFrame()
    {
        return frame;
    }

    /**
     * odpowiada za ustawienie elementow w oknie gry
     */
    private void initialize() throws IOException {
        frame = new JFrame("Diamonds");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0,0);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(1000,700));
        canvas.setFocusable(false);
        canvas.setBackground(Color.CYAN);


        input = new JTextField();
        input.setPreferredSize(new Dimension(800,30));
        input.setFocusable(true);

        showText = new JTextArea(3, 20);
        showText.setFocusable(false);


        JScrollPane scroll = new JScrollPane(showText);
        scroll.setPreferredSize(new Dimension(800,100));


        JPanel jPanel1 = new JPanel();
        jPanel1.add(scroll);
        jPanel1.setBackground(Color.CYAN);

        JPanel jPanel2 = new JPanel();
        jPanel2.add(input);
        jPanel2.setBackground(Color.CYAN);



        frame.add(canvas, BorderLayout.NORTH);
        frame.add(jPanel1, BorderLayout.CENTER);
        frame.add(jPanel2, BorderLayout.SOUTH);
        frame.pack();


        Game game = new Game(1000 , 800,this);
        client.setGame(game);
        game.t = client;
        game.start();
    }


}


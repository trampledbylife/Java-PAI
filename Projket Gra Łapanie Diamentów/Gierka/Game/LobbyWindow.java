package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * LobbyWindo odpowiada za wyswietlanie okna poczeklani
 * z mozliwoscia wyboru nazwy gracza i poczeklani
 */
public class LobbyWindow {

    int width;
    int height;

    private JFrame frame;
    public Client client;

    /**
     * @param width  szerokosc okna
     * @param height wysokosc okna
     */
    public LobbyWindow(int width , int height) throws IOException {

        if(width<0 || height<0)
        {
            throw new IOException("less thne zero");
        }
        this.width = width;
        this.height = height;

        init();
    }

    /**
     * odpowiada za ustawienie elementow w oknie poczeklni
     */
    public void init() throws IOException {

        Socket s = new Socket("localhost", 1234);
        client = new Client(s);

        frame = new JFrame("Diamonds Lobby");
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        Font font = new Font(Font.SANS_SERIF,  Font.BOLD, 20);
        TextField input = new TextField();
        input.setFont(font);
        input.setPreferredSize(new Dimension(100,30));
        input.setVisible(true);

        TextField input2 = new TextField();
        input2.setFont(font);
        input2.setPreferredSize(new Dimension(100,30));
        input2.setVisible(true);


        JLabel error = new JLabel();
        error.setText("");
        error.setForeground(Color.red);
        error.setVisible(true);
        error.setAlignmentY(Component.LEFT_ALIGNMENT);

        JButton button1 = new JButton();
        button1.setText("JOIN");


        /**
         * Odpowiada za wykonanie akcji po nacisneiciu guzika
         */
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String message = input.getText();
                String nickname = input2.getText();

                if(message.length()>0 && nickname.length()>0)
                {
                    int result = client.joinLobby(message);
                    if(result == 0){

                        client.setNickname(nickname);
                        try {
                            GameWindow window = new GameWindow(width,height,client);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        frame.dispose();
                    }else
                    {
                        error.setText("THIS LOBBY IS FULL");
                    }
                }
                else
                {
                    error.setText("FILL ALL GAP");
                }
            }
        });

        button1.setPreferredSize(new Dimension(100,30));
        button1.setVisible(true);

        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(10,10,10,10);

        JLabel label11 = new JLabel();
        label11.setText("Nickname:");
        label11.setVisible(true);
        label11.setAlignmentY(Component.LEFT_ALIGNMENT);

        JLabel label1 = new JLabel();
        label1.setText("Game password:");
        label1.setVisible(true);
        label1.setAlignmentY(Component.LEFT_ALIGNMENT);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setVisible(true);

        grid.gridx = 0;
        grid.gridy = 1;
        panel.add(label11,grid);

        grid.gridx = 1;
        grid.gridy = 1;
        panel.add(input2,grid);

        grid.gridx = 0;
        grid.gridy = 2;
        panel.add(label1,grid);

        grid.gridx = 1;
        grid.gridy = 2;
        panel.add(input,grid);

        grid.gridx = 1;
        grid.gridy = 3;
        panel.add(button1,grid);


        grid.gridx = 0;
        grid.gridy = 3;
        panel.add(error,grid);

        frame.add(panel);
    }

}

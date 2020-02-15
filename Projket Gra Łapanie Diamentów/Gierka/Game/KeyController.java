package Game;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * KeyController odpowiada za przetwarzanie sygnalow z kalwiatury
 */
public class KeyController implements KeyListener {

    private Player player;
    private GameWindow window;
    private Client client;

    /**
     * @param player informacje o graczu
     * @param window dostep do okna w koorym wyswietlana jest gra
     * @param client umozliwa poalczenie z serwerem
     */
    public KeyController(Player player, GameWindow window, Client client)
    {
        this.player = player;
        this.window = window;
        this.client = client;
    }

    /** wcisniecie klawisza
     * @param e sygnal odebrany z klawiatury
     */
    @Override
    public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                this.player.left = true;
            }

            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                this.player.right = true;
            }

            if(e.getKeyCode() == KeyEvent.VK_UP){
                this.player.up = true;
            }

            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                this.player.down = true;
            }


            if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                int check = this.player.colorNumber;

                check++;

                if(check>3){
                    check=1;
                }
                this.player.colorNumber=check;
            }

            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                String message = window.input.getText();
                client.sendMessage("#id" +player.id+ " " +player.nickname +": "+message);
                window.showText.append("#id" +player.id+ " " +player.nickname +": "+message);
                window.showText.append("\n");
                window.input.setText("");
            }
    }

    /** odpuszczenie kalwisza
     * @param e sygnal odebrany z klawiatury
     */
    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            this.player.left = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            this.player.right = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            this.player.up = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            this.player.down = false;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }
}

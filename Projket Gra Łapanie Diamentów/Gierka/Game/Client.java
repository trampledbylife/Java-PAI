package Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Client odpowiada za wysyłanie i odbieranie wiadomsoci z Serwera
 */
public class Client extends Thread
{
    Game game;
    public DataOutputStream dos = null;
    public DataInputStream dis = null;
    public String nicknameTemp;


    /**
     * @param s soket umozlwiajacy poalczenie z serverem
     */
    public Client(Socket s) throws IOException{

        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());

    }

    public void run()
    {
        try {

            game.player.id = dis.readInt();
            game.player.x  = dis.readInt();
            game.player.nickname = nicknameTemp;
            while (true) {

                sendUpdatedPosition();

                String message = dis.readUTF();
                if(message.startsWith("SET_ENEMY")){

                    String[] cords = message.split(":");
                    String[] numbers = cords[1].split(",");

                    this.game.enemyPlayer.add(new Player(0,Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]),0,0,0, null));

                    sendPlayerReady();

                }
                else if(message.equals("POSITION_UPDATE")){
                    int x = dis.readInt();
                    int y = dis.readInt();
                    int colorNumber = dis.readInt();
                    int score = dis.readInt();

                    this.game.enemyPlayer.get(0).x = x;
                    this.game.enemyPlayer.get(0).y = y;
                    this.game.enemyPlayer.get(0).colorNumber = colorNumber;
                    this.game.enemyPlayer.get(0).scoreStatus = score;

                }
                else if(message.equals("ENEMY_MESSAGE")){
                    String m = dis.readUTF();
                    this.game.window.showText.append(m);
                    this.game.window.showText.append("\n");
                }

                else if(message.equals("GAME_IS_READY")){
                    this.game.player.foodShow = 1;
                }
                else if(message.equals("FOOD_UPDATE")){
                    for (int i = 0; i <= 5; i++) //5
                    {
                        this.game.player.foodRender[i] =  dis.readInt();
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


    /**
     * wysyla wiadomosci do serwera o aktualnym polozeniu gracza
     */
    public void sendUpdatedPosition()
    {

        try {
            synchronized (dos) {
                dos.writeUTF("POSITION_UPDATE");
                dos.writeInt( game.player.x);
                dos.writeInt( game.player.y);
                dos.writeInt(game.player.colorNumber);
                dos.writeInt(game.player.scoreStatus);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }


    /**
     * @param message wiadomosc wpisana przez gracza wysylana na serwer
     */
    public void sendMessage(String message){
        try{
            synchronized (dos){
                dos.writeUTF("PLAYER_MESSAGE");
                dos.writeUTF(message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * wysyla informacje o gotowosci
     */
    public void sendPlayerReady(){
        try{
            synchronized (dos){
                dos.writeUTF("PLAYER_IS_READY");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * wysyla wiadomosci do serwera o aktualnym polozeniu jedzenia
     */
    public void updateFood(){
        try{
            synchronized (dos){
                dos.writeUTF("FOOD_UPDATE");
                for (int i = 0; i <= 5; i++) //5
                {
                    dos.writeInt(game.player.foodRender[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param message informacja jaka am zostac wyslana do serwera by otrzymac informacje o poczeklani
     * @return infdormacja o powodzeniu lub porazce dolaczenia do poczeklani
     */
    public int joinLobby(String message){
        try {
            dos.writeUTF(message);
            String answer = dis.readUTF();
            if(answer.equals("LOBBY_ERROR")) {
                return 1;
            }
            else {
                return 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param game jest ustawiany jako glowna Gra
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @param nickname pobiera nazwę gracza
     */
    public void setNickname(String nickname) {
        nicknameTemp = nickname;
    }
}

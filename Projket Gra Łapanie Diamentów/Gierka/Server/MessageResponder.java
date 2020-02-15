package Server;

import java.util.List;

/**
 * MessageResponder odpowiada za odbieranie i wysyłanie wiadomosci do Clienta
 */
public class MessageResponder extends Thread {
    List<PlayerInfo> enemyPlayer = null;
    PlayerInfo currentPlayer;


    /**
     * @param enemyPlayer lista użytkowników w danej poczekalni maksymalnie dwóch
     * @param player na jego podstawie bedzie okreslany adresat i odbiorca wiadomosci
     */
    public MessageResponder(List<PlayerInfo> enemyPlayer, PlayerInfo player)  {
        this.enemyPlayer = enemyPlayer;
        this.currentPlayer = player;
    }


    public void run() {
        try {
            System.out.println(enemyPlayer.size());
            String request = null;


            for (PlayerInfo data : enemyPlayer) {
                if (data != currentPlayer) {
                    data.dos.writeUTF("SET_ENEMY:" + currentPlayer.x + "," + currentPlayer.y);
                }
            }

            for (PlayerInfo data : enemyPlayer) {
                if (data != currentPlayer) {
                    currentPlayer.dos.writeUTF("SET_ENEMY:" + data.x + "," + data.y);
                }
            }
            while (true) {

                request = currentPlayer.dis.readUTF();
                if (request.equals("POSITION_UPDATE")) {
                    int n_x = currentPlayer.dis.readInt();
                    int n_y = currentPlayer.dis.readInt();
                    int n_color = currentPlayer.dis.readInt();
                    int n_score = currentPlayer.dis.readInt();


                    currentPlayer.x = n_x;
                    currentPlayer.y = n_y;
                    currentPlayer.colorNumber = n_color;
                    currentPlayer.scoreStatus = n_score;


                    for (PlayerInfo player : enemyPlayer) {
                        if (player != this.currentPlayer) {
                            player.dos.writeUTF("POSITION_UPDATE");
                            player.dos.writeInt(this.currentPlayer.x);
                            player.dos.writeInt(this.currentPlayer.y);
                            player.dos.writeInt(this.currentPlayer.colorNumber);
                            player.dos.writeInt(this.currentPlayer.scoreStatus);

                        }
                    }
                } else if (request.equals("PLAYER_MESSAGE")) {
                    String message = currentPlayer.dis.readUTF();
                    for (PlayerInfo data : enemyPlayer) {
                        if (data.id != this.currentPlayer.id) {
                            data.dos.writeUTF("ENEMY_MESSAGE");
                            data.dos.writeUTF(message);
                        }
                    }
                } else if (request.equals("PLAYER_IS_READY")) {
                    Thread.sleep(3000);
                    for (PlayerInfo data : enemyPlayer) {
                        if (data.id != this.currentPlayer.id) {
                            data.dos.writeUTF("GAME_IS_READY");
                        }
                    }
                }
                else if (request.equals("FOOD_UPDATE")) {

                    for (int i = 0; i <= 5; i++) //5
                    {
                        currentPlayer.food[i] = currentPlayer.dis.readInt();
                    }


                    for (PlayerInfo data : enemyPlayer) {
                        if (data.id != this.currentPlayer.id) {
                            data.dos.writeUTF("FOOD_UPDATE");
                            for (int i = 0; i <= 5; i++) //5
                            {
                                data.dos.writeInt(this.currentPlayer.food[i]);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
}
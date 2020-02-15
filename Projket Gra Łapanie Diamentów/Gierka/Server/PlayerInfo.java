package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * PlayerInfo odpowiada za przechowywanie informacji o danym graczu
 */
public class PlayerInfo
{
    int id;
    int x=0;
    int y=0;
    int colorNumber=0;
    int scoreStatus=0;
    int[] food = new int[6];
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    /**
     * @param id jets to id użytkownika
     * @param socket unikalny socket użytkonika do komnikacji Client/Server
     */
    public PlayerInfo(int id, Socket socket)
    {

        try {
            this.id = id;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){

        }
        for (int i = 0; i <= 5; i++) //5
        {
            food[i] = 1;
        }
    }
}

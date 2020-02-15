package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;



/**
 * Glowna klasa Server zostaje z niej wywolany watek zarzadzajacy praca serwera
 */
public class Server extends Thread
{
    ServerSocket ss;
    int port;
    String host;

    List<PlayerInfo> players = new ArrayList<PlayerInfo>();

    public static void main(String[] args){
        Thread serverThread = new Server();
        serverThread.start();

    }


    /** readXML służy do odczytania konfiguracji servera  z pliku XML
     * @param configuration  nazwa pliku z rozszerzeniem XML
     */
    private void readXML(String configuration){
        try {
            File f = new File(configuration);
            FileInputStream fis = new FileInputStream(f);
            Properties properties = new Properties();
            properties.loadFromXML(fis);
            fis.close();

            Enumeration enuKeys = properties.keys();
            String key;

            while (enuKeys.hasMoreElements()) {
                switch(key = (String) enuKeys.nextElement())
                {
                    case "port":
                        this.setPort(Integer.parseInt(properties.getProperty(key)));
                        break;
                    case "host":
                        setHost(properties.getProperty(key));
                        break;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param port jest ustawiany na port
     */
    private void setPort(int port){
        this.port = port;
    }

    /**
     * @param host jest ustawiany na host
     */
    private void setHost(String host){
        this.host = host;
    }

    @Override
    public void run(){
        int idNumber=0;
        this.readXML("Gierka\\Server\\ServerConfiguration\\configuration.xml");

        try {
            ss = new ServerSocket(port);
        }catch (Exception e){
            System.out.println("Server error.");
        }

        System.out.println("SERVER IS READY");

        HashMap<String, ArrayList<PlayerInfo>> lobby = new HashMap<>();

        while(true){
            try {
                PlayerInfo player;
                String password;
                Socket socket = ss.accept();

                DataInputStream userInput = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                while(true) {
                    password = userInput.readUTF();
                    if (lobby.containsKey(password)) {
                        System.out.println(lobby.get(password).size());
                        if (lobby.get(password).size() == 2) {
                            dos.writeUTF("LOBBY_ERROR");
                        }else if(lobby.get(password).size() == 1){
                            dos.writeUTF("AVAILABLE");
                            player = new PlayerInfo(2, socket);
                            lobby.get(password).add(1,player);
                            dos.writeInt(2);
                            dos.writeInt(750);
                            System.out.println(lobby.get(password));
                            break;
                        }
                    }else {
                        dos.writeUTF("AVAILABLE");
                        ArrayList<PlayerInfo> lobbyMember = new ArrayList<>();
                        player = new PlayerInfo(1, socket);
                        lobbyMember.add(player);
                        dos.writeInt(1);
                        dos.writeInt(150);
                        lobby.put(password,lobbyMember);
                        break;
                    }
                }


                Thread responder = new MessageResponder(lobby.get(password),player);
                responder.start();
                idNumber++;
                if(idNumber>3)
                {
                    idNumber = 0;
                }
                players.add(player);



            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

}



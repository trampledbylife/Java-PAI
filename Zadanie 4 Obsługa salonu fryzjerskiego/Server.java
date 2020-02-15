import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.WrongMethodTypeException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static ArrayList<Messages> hairSalonClients;
    private static int  ClientNumber = 0;
    
    public static void main(String[] args) {
        if(!init()){
            return;
        }
        try(ServerSocket serverSocket = new ServerSocket(1234)){
            while(true)
            {
                Messages newMessage = new Messages(serverSocket.accept(), ClientNumber);
                hairSalonClients.add(newMessage);
                newMessage.start();
                
                ClientNumber++;
            }

        } 
        catch (IOException e)
        {
            System.out.println("Error " + e.getMessage());
        }
    }

    private static boolean init()
    {
        try
        {
            System.out.println("Server dzia³a!");
            ClientNumber = 0;
            hairSalonClients = new ArrayList<>();
            return true;
        } 
        catch (Exception e)
        {
            System.out.println("Error " + e.getMessage());
            return false;
        }
    }

    public static boolean InformClients(Messages newMessage, String message) 
    {
        try 
        {
            for (Messages client : Server.getClients())
             {
                if (!client.equals(newMessage)) 
                {
                    client.getOutput().println("==========================================================");                 
                    client.getOutput().println(">>" +message + "  Oto nowy terminarz:<<");
                    client.getOutput().println(WorkTimetable.getInstance().PrintfForClient());
                    client.getOutput().println("kontynnuj wprowadzanie danych...");
                    client.getOutput().println("==========================================================");
                }
            }
            return true;
        } 
        catch (Exception e) 
        {
            System.out.println("Error" + e.getMessage());
            return false;
        }
    }

    public static ArrayList<Messages> getClients() 
    {
        return hairSalonClients;
    }
}




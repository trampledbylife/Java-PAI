import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static String inputString;

    public static void main(String[] args) {
        
        try
        {
          socket = new Socket("localhost", 1234);
          reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          writer = new PrintWriter(socket.getOutputStream(),true);
        } 
        catch (IOException e)
        {
            System.out.println("E " + e.getMessage());
        }
        
        inputString = "";
    
        GetMessageFromServer newMessage = new GetMessageFromServer();
        newMessage.start();
        try
        {
            Scanner scanner = new Scanner(System.in);
            do 
            {
                inputString = scanner.nextLine();
                writer.println(inputString);
            } 
            while(!inputString.equals("0"));
        } 
        catch (InputMismatchException e)
        {
            System.out.println("Error: " + e.getMessage());
        } 
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if(socket != null) 
                {
                    socket.close();
                    reader.close();
                    writer.close();
                }
            } 
            catch (IOException e)
            {
                System.out.println("Error" + e.getMessage());
            }
        }
    }


    static String getString() {
        return inputString;
    }

    static BufferedReader getReader() {
        return reader;
    }
}


class GetMessageFromServer extends Thread{

    @Override
    public void run() {
        String newMessage;
        try{
            
           do{
                newMessage = Client.getReader().readLine();
                if(!(newMessage == null)) 
                {      
                    System.out.println(newMessage);
                }
            } 
            while(!Client.getString().equals("0"));
            System.out.println("Zakonczono");
        } 
        catch (IOException e){
            System.out.println(e.getMessage());
        }  
    }

}
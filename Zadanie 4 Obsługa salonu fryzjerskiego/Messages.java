import java.io.*;
import java.net.Socket;

public class Messages extends Thread{
    private Socket socket;
    private int clientID;
    private BufferedReader input;
    private PrintWriter output;

    private String stringHour;
    private String ID;
    private int hour;

    Messages(Socket socket, int clientID) {
        this.socket = socket;
        this.clientID = clientID;
        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e){
            System.out.println("Error " + e.getMessage());
        }
        
        output.println(">>SYSTEM REZERWACJI WIZYTY U FRYZJERA<<\n");
        output.println(WorkTimetable.getInstance().PrintfForClient());
    }

    @Override
    public void run() {
        try{
            
            output.println("\nDOSTEPNE OPCJE:");
            output.println("0 - zamknij program");
            output.println("1 - zarezerwuj wizyt�");
            output.println("2 - anuluj wizyt�");
            
            while(true)
            {
                String echoString = input.readLine();
                
                if(echoString.equals("0") || echoString == null)
                {
                    break;
                }
                
                switch (echoString)
                {
                    case "1":
                        DoNewAppointment();
                        break;
                    case "2":
                        CancelAppointment();
                        break;    
                    default:
                        output.println("Z�y wyb�r, spr�buj jeszcze raz");
                        break;
                }
            }

        } 
        catch (IOException e)
        {
            System.out.println("Error " + e.getMessage());
        } 
        
        finally{
            try {
                if(socket != null) 
                {
                    output.println("Zako�czono dzia�anie programu.");
                    socket.close();
                    input.close();
                    output.close();   
                }
            } 
          catch (IOException e)
          {
                System.out.println("Error " + e.getMessage());
            }
        }

    }

    private boolean DoNewAppointment() throws IOException
    {
        do
        {
            output.println("Wybierz godzin� rezerwacji: ");
            stringHour = input.readLine();
            hour = validateHour(stringHour);
        }while(hour == 0);
        
       
        do
        {
          output.println("Wprowad� imi� i nazwisko:"); 
          ID = input.readLine();
        }while(validateID(ID) != true);
        
        
        if(WorkTimetable.getInstance().SetNewAppointment(hour, ID))
        {
            output.println("Poprawnie zarezerwowano wizyt� o godzinie " + hour + ":00."); //wiadomosc dla obecnego clienta
            
            System.out.println(">>>Masz masz now� wizyt�!<<<\n" + WorkTimetable.getInstance().PrintfForHairdresser()); //wiadomosc dla fryzjera
            
            Server.InformClients(this,"Jeden z termin�w zosta� zarezerwowany!"); //wiadomosc dla reszty clientow
        } 
        else 
        {
            output.println("Error. Nie uda�o si� zarezerwowa� wizyty.");
            return false;
        }
        
        hour = 0;
        return true;
    }



    private boolean CancelAppointment() throws IOException
    {
  
        do
        {
            output.println("Aby anulowa� wizyt� wprowad� um�wion� godzin�: ");
            stringHour = input.readLine();
            hour = validateHour(stringHour);
        }while(hour == 0);
         
        do
        {
          output.println("Wprowad� imi� i nazwisko:"); 
          ID = input.readLine();
        }while(validateID(ID)!=true);
        
        
        
        if(WorkTimetable.getInstance().DeleteAppointment(hour, ID))
        {
            output.println("Uda�o anulowa� si� twoj� wizyt� o godzinie " + hour + ":00."); //wiadomosc dla obecnego clienta
            
            System.out.println(">>>Klient anulowa� wizyt�<<<\n" + WorkTimetable.getInstance().PrintfForHairdresser()); //wiadomosc dla fryzjera
            
            Server.InformClients(this,"Jeden z termin�w zosta� zwolnjiony!"); //wiadomosc dla reszty clientow
        } 
        else 
        {
            output.println("Error. Nie uda�o si� anulowa� wizyty.");
            return false;
        }
        
        hour = 0;
        return true;
    }


    private boolean validateID(String ID) 
    { 
         boolean returned = false;
         returned = ID.contains(" ");
         
         if(returned == false) output.println("Z�y format! (Imie Nazwisko). Wprowadz jeszcze raz");
         return  returned;
    }



    private int validateHour(String stringHour) 
    { 
         int value = 0;
         try 
         {   
               if (stringHour.length() == 5 && stringHour.substring(2).equals(":00")) 
               { 
                   stringHour = stringHour.substring(0, 2); 
                   if(stringHour.matches("[0-9]+")) 
                   {
                        value = Integer.parseInt(stringHour);
                        if(value >= 10 && value <= 18) return value;
                   }
              }    
          } 
          catch (NumberFormatException e)
          {
              System.out.println(e.getMessage());
          }
    
        output.println("Wprowadzono z�� godzin�, spr�buj jeszcze raz");
        return  0;
    }

    PrintWriter getOutput() {
        return output;
    }
}

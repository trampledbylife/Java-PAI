import java.util.HashMap;
import java.util.Map;

public class WorkTimetable {

    private String free_place = "wolne";
    private static WorkTimetable newTimetable;

    private Map<Integer, String> new_hour = new HashMap<>(9);

    private WorkTimetable() {
        new_hour.put(10, free_place);
        new_hour.put(11, free_place);
        new_hour.put(12, free_place);
        new_hour.put(13, free_place);
        new_hour.put(14, free_place);
        new_hour.put(15, free_place);
        new_hour.put(16, free_place);
        new_hour.put(17, free_place);
        new_hour.put(18, free_place);
    }

    public static WorkTimetable getInstance(){
        if (newTimetable == null) 
        {
            synchronized (WorkTimetable.class)
            {
                if (newTimetable == null) 
                {
                    newTimetable = new WorkTimetable();
                }
            }
        }
        return newTimetable;
    }

    
    boolean SetNewAppointment(int hour, String ID)
    {
        if(new_hour.get(hour).equals(free_place))
        {
            new_hour.replace(hour, ID);
            return true;
        }
        return false;
    }


    boolean DeleteAppointment(int hour, String ID)
    {
        if(new_hour.get(hour).equals(ID))
        {
            new_hour.replace(hour, free_place);
            return true;
        }
        return false;
    }

    String PrintfForHairdresser()
    {
        StringBuffer allTable = new StringBuffer("TWÓJ HARMONOGRAM:\n");
        for(int i = 0; i<new_hour.size(); i++)
        {
           allTable.append((i + 10) + ":00 " + new_hour.get(i+10) +"\n" );  
        }
        return allTable.substring(0, allTable.length()-1);
    }


    String PrintfForClient(){
        StringBuffer allTable = new StringBuffer("TERMINY PRZYJÊÆ:\n");
        for(int i = 0; i<new_hour.size(); i++)
        {
            if (new_hour.get(i+10).equals(free_place))
            {
                allTable.append((i + 10) + ":00   WOLNY\n");
            }
            else
            {
              allTable.append((i + 10) + ":00   ZAJÊTY\n");
            }
        }
        return allTable.substring(0, allTable.length()-1);
    }


}
public class Deadlock {


    private static Object objectLockA = new Object();
    private static Object objectLockB = new Object();
 
    public static void firstLock() {
        synchronized (objectLockA)
        {
            synchronized (objectLockB)
            {
                System.out.println("Waiting for second...");
            }
        }
    }
 
    public static void secondLock()
    {
        synchronized (objectLockB)
        {
            synchronized (objectLockA)
            {
                System.out.println("Waiting for first...");
            }
        }
    }

    public static void start()
    {

        Thread first = new Thread(new Runnable() {

            public void run()
            {
                firstLock();
               /* try
                {
                    Thread.sleep(300);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }*/
            }
        });

        first.start();

        Thread second = new Thread(new Runnable() {

            public void run()
            {
                secondLock();
                /*try
                {
                   // Thread.sleep(300);
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                }*/
            }
        });

        second.start();
    }
}

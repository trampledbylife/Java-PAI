public class Starvation {
    public static class Action {

        public synchronized void start() {
            String ThreadNum = Thread.currentThread().getName();

           while (true)
           {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("Working " + ThreadNum);

           }
        }
    }

    public void start() {
        Action action = new Action();

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {
                public void run()
                {
                    action.start();
                }
            });
            thread.start();
        }
    }
}

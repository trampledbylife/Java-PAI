public class Livelock {

    public static class ActionA {

        private boolean firstDone = false;

        public void firstLockRun(ActionB second)
        {
            while (!second.secondStatus())
            {

                System.out.println("First wait for second...");

                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }

            System.out.println("First Done");
            this.firstDone = true;
        }

        public boolean firstStatus()
        {
            return this.firstDone;
        }
    }

    public static class ActionB {

        private boolean secondDone = false;

        public void secondLockRun(ActionA first)
        {
            while (!first.firstStatus())
            {

                System.out.println("Second wait for first...");

                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }

            System.out.println("Second Done");
            this.secondDone = true;
        }

        public boolean secondStatus()
        {
            return this.secondDone;
        }
    }

    static final ActionB second = new ActionB();
    static final ActionA first = new ActionA();

    public static void start() {

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                first.firstLockRun(second);
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                second.secondLockRun(first);
            }
        });
        thread2.start();
    }
}

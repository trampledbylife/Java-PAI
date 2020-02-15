class Main {
    public static void main(String[] args) {

        String choice = args[0];

        try {
            switch (choice)
            {
                case "deadlock":
                    Deadlock deadlockRun = new Deadlock();
                    deadlockRun.start();
                    break;


                case "livelock":
                    Livelock livelockRun = new Livelock();
                    livelockRun.start();
                    break;

                case "starvation":
                    Starvation starvationRun = new Starvation();
                    starvationRun.start();

                    break;

                default:
                    System.out.println("wrong choice");
                    break;
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error");
        }

        double curentime = System.currentTimeMillis();
        while(true)
        {
            if(System.currentTimeMillis() - 5000 > curentime)
            {
                System.exit(0);
            }
        }
    }
}


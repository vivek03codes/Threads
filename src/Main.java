class Main {
    public static void main(String[] args) {
        Test test = new Test(); //Instance of Class extending Thread
        Test1 test1 = new Test1(); //Instance of Class implementing Runnable
        Thread t1 = new Thread(test1); // Creating instance of Thread & passing the Runnable instance

        // Starting Thread
//        test.start();
//        t1.start();

        //Synchronisation
        Counter counter = new Counter();
        CounterThread counterThread1 = new CounterThread(counter);
        CounterThread counterThread2 = new CounterThread(counter);

        counterThread1.start();
        counterThread2.start();

        try {
            counterThread1.join();
            counterThread2.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(counter.getCount());

    }
}
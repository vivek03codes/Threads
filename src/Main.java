class Main {
    public static void main(String[] args) {
        Test test = new Test(); //Instance of Class extending Thread
        Test1 test1 = new Test1(); //Instance of Class implementing Runnable
//        Thread t1 = new Thread(test1); // Creating instance of Thread & passing the Runnable instance

        // Starting Thread
//        test.start();
//        t1.start();

        //Synchronisation
        Counter counter = new Counter();
        CounterThread counterThread1 = new CounterThread(counter);
        CounterThread counterThread2 = new CounterThread(counter);

//        counterThread1.start();
//        counterThread2.start();
//
//        try {
//            counterThread1.join();
//            counterThread2.join();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(counter.getCount());

        //Locks
        /*
         are of two types Intrinsic & Extrinsic
         Intrinsic locks are automatic locks which are present on all objects in Java and apply automatically when a Thread uses the resource which is marked as synchronized.
         Extrinsic locks gives you more control on when to lock and unlock the resource. It uses the Lock class. The below example is using Extrinsic locks using ReentrantLock
         */
        BankAccount bankAccount = new BankAccount();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                bankAccount.withdraw(50);
            }
        };

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");

        t1.start();
        t2.start();

    }
}
class Main {
    public static void main(String[] args) {
        Test test = new Test(); //Instance of Class extending Thread
        Test1 test1 = new Test1(); //Instance of Class implementing Runnable
        Thread t1 = new Thread(test1); // Creating instance of Thread & passing the Runnable instance

        // Starting Thread
        test.start();
        t1.start();

        //Main Thread
        for (; ;) {
            System.out.println("Main Thread");
        }
    }
}
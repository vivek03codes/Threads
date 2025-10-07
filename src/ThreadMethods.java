public class ThreadMethods extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println(Thread.currentThread().getName() + "is running");
        Thread.yield(); // A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore this hint.
    }

    static void main() throws InterruptedException {
        ThreadMethods methods = new ThreadMethods();
        methods.start();
        methods.join();
        //Interrupts the running of the Thread and throws InterruptedException
        methods.interrupt();
        //Priority hints for the scheduler to execute threads with high priority first (Scheduler can ignore these hints)
        methods.setPriority(Thread.MAX_PRIORITY);
        methods.setPriority(Thread.NORM_PRIORITY);
        methods.setPriority(Thread.MIN_PRIORITY);
        //Background Threads for which JVM does not wait and finishes it's execution when the Main Thread and all user threads have completed execution
        methods.setDaemon(true);

    }
}

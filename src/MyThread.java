public class MyThread extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("RUNNING");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    static void main() throws InterruptedException {
        // LIFE CYCLE OF THREADS
        MyThread t1 = new MyThread(); //NEW - A Thread is being created but not running
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE - A Thread is ready to run or being executed
        Thread.sleep(100);
        System.out.println(t1.getState()); // TIME-WAITING - When a Thread is waiting for its execution
        t1.join(); // Tells the main thread to start execution when t1 thread is completed
        System.out.println(t1.getState()); // TERMINATED -  A Thread has completed its execution and now is terminated
    }
}

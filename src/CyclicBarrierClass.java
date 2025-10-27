import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierClass {
    static void main() {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            System.out.println("All system initialized");
        });

        Thread webServerThread = new Thread(new SubSystem(barrier, "web server", 2000));
        Thread databaseThread = new Thread(new SubSystem(barrier, "database", 3000));
        Thread cacheThread = new Thread(new SubSystem(barrier, "cache", 1000));
        Thread cloudMessagingThread = new Thread(new SubSystem(barrier, "cloud-messaging", 2000));

        webServerThread.start();
        databaseThread.start();
        cacheThread.start();
        cloudMessagingThread.start();

        System.out.println("Main");



    }
}

class SubSystem implements Runnable {

    private final CyclicBarrier barrier;
    private final String name;
    private final int initializationTime;

    SubSystem(CyclicBarrier barrier, String name, int initializationTime) {
        this.barrier = barrier;
        this.name = name;
        this.initializationTime = initializationTime;
    }


    @Override
    public void run() {

        try {
            System.out.println(name + " started initializing");
            Thread.sleep(initializationTime);
            System.out.println(name + " initialized, waiting at barrier");
            barrier.await(); //Waits till the last thread has invoked await on this barrier, does not block the main thread
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }
}

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairnessExample {
    private final Lock lock = new ReentrantLock(true);

    public void task() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " holding the lock");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(Thread.currentThread().getName() + " releasing the lock");
                lock.unlock();
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    static void main() {
        FairnessExample obj = new FairnessExample();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                obj.task();
            }
        };

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        Thread t3 = new Thread(task, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}

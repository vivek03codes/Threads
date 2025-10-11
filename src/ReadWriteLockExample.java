import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    private int count = 0;

    void increment() {
        writeLock.lock();
        try {
            count++;
        } finally {
            System.out.println(Thread.currentThread().getName() + " incremented count");
            writeLock.unlock();
        }
    }

    void getCount() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading value of count: " + count);
        } finally {
            readLock.unlock();
        }
    }

    static void main() {
        ReadWriteLockExample example = new ReadWriteLockExample();

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    example.increment();
                }
            }
        };

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    example.getCount();
                }
            }
        };

        Thread writeThread = new Thread(writeTask, "Write Thread");
        Thread readThread1 = new Thread(readTask, "Read Thread 1");
        Thread readThread2 = new Thread(readTask, "Read Thread 2");

        writeThread.start();
        readThread1.start();
        readThread2.start();

        try {
            writeThread.join();
            readThread1.join();
            readThread2.join();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}

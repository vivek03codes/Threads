import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private final Lock lock = new ReentrantLock();

    public void outerMethod() {
        lock.lock(); // 1st lock
        try {
            System.out.println("Outer Method");
            innerMethod();
        } finally {
            lock.unlock(); // 2nd unlock
        }
    }

    public void innerMethod() {
        lock.lock(); // 2nd lock // Because the lock is acquired by the same thread, it can access it thus preventing deadlock condition
        try {
            System.out.println("Inner Method");
        } finally {
            lock.unlock(); // 1st unlock
        }
    }

    /*
    ReentrantLock has a count based locking thus to unlock any resource, it should be unlocked as many times as it was locked.
    In the above example, there are 2 lock.lock() statements meaning the resource was locked 2 times, so to make the resource free again it needs to be unlocked 2 times.
     */

    static void main() {
        ReentrantLockExample exm = new ReentrantLockExample();
        exm.outerMethod();
    }
}

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance = 100.0;

    private final Lock lock = new ReentrantLock();

    public void withdraw(double amount) {
        System.out.println(Thread.currentThread().getName() + " proceeded to withdraw " + amount);
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                if (balance >= amount) {
                    System.out.println(Thread.currentThread().getName() + " processing withdraw");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + " completed withdrawal. Remaining balance: " + balance);
                } else {
                    System.out.println(Thread.currentThread().getName() + " Insufficient balance");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " Can't acquire lock, will try again later");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}

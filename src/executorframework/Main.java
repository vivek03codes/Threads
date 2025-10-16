package executorframework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    static void main() {
        /*Manual Multithreading
        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[9];
        for (int i = 1; i < 10; i++) {
            int finalI = i;
            threads[i - 1] = new Thread(() -> {
                System.out.println(factorial(finalI));
            });
            threads[i - 1].start();
        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Total time: "+ (System.currentTimeMillis() - startTime));
         */

        //Using Executor Framework
        /*
        Handled Thread creation & managing the threads on its own
        Can reuse the thread unlike manually created threads which can't be reused
         */
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(9);
        for (int i = 1; i < 10; i++) {
            int finalI = i;
            executor.submit(() -> System.out.println(factorial(finalI)));
        }
        executor.shutdown(); // Will shut down the executor service when all threads are finished execution, but will not wait for their execution to get finished.
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS); //Waits for all the executors for the given time to finish their task and then only moves to the next line.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Total time: "+ (System.currentTimeMillis() - startTime));

    }


    public static int factorial(int n) {
        int factorial = 1;
        if (n > 0) {
            for (int i = n; i > 0; i--) {
                factorial *= i;
            }
            return factorial;
        } else {
            return -1;
        }
    }
}

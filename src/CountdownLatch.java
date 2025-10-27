import java.util.concurrent.*;

public class CountdownLatch {

    static void main() throws ExecutionException, InterruptedException {

        int numberOfServices = 3;

        ExecutorService service = Executors.newFixedThreadPool(numberOfServices);

        CountDownLatch latch = new CountDownLatch(numberOfServices);

        Future<String> future1 = service.submit(new DependentService(latch));
        Future<String> future2 = service.submit(new DependentService(latch));
        Future<String> future3 = service.submit(new DependentService(latch));

//        future1.get();
//        future2.get();
//        future3.get();

        latch.await(); // Using CountDownLatch a more simplistic approach. CountDownLatch waits for the number of services to be executed and then frees the Main thread

        System.out.println("Resuming Main thread");
        service.shutdown();

    }
}

class DependentService implements Callable<String> {

    private final CountDownLatch latch;

    DependentService(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String call() throws Exception {
        try {
            System.out.println("Dependent service started!!");
            Thread.sleep(2000);
        } finally {
            latch.countDown();
        }
        return "";
    }
}

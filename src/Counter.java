public class Counter {
    private int count;

    //marked as synchronized for the threads to mutually access the resource to avoid race conditions
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

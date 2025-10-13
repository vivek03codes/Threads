class Pen {
    public synchronized void writeWithPenAndPaper(Paper paper) {
        System.out.println(Thread.currentThread().getName() + " is using pen " + this + " and trying to write " + paper);
        paper.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " finished using pen " + this);
    }
}

class Paper {
    public synchronized void writeWithPaperAndPen(Pen pen) {
        System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to write " + pen);
        pen.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " finished using paper " + this);
    }
}

class Task1 implements Runnable {

    private final Pen pen;
    private final Paper paper;

    Task1(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        pen.writeWithPenAndPaper(paper); //Thread1 locks pen and tries to lock paper
    }
}

class Task2 implements Runnable {
    private final Pen pen;
    private final Paper paper;

    Task2(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        synchronized (pen) {
            paper.writeWithPaperAndPen(pen); //Thread2 locks paper and tries to lock pen
        }

    }
}

public class DeadlockExample {

    static void main() {
        Pen pen = new Pen();
        Paper paper = new Paper();

        Task1 task1 = new Task1(pen, paper);
        Task2 task2 = new Task2(pen, paper);

        Thread thread1 = new Thread(task1, "Thread 1");
        Thread thread2 = new Thread(task2, "Thread 2");

        thread1.start();
        thread2.start();
    }

}

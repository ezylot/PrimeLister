import java.util.ArrayDeque;
import java.util.Deque;

public class BufferedPrinter implements Runnable {

    private final Object lock = new Object();
    private final Object lock2 = new Object();

    private Deque<String> messages = new ArrayDeque<>();
    private long lineCounter = 1;

    private String getMessage() {
        synchronized (lock) {
            return messages.removeFirst();
        }
    }

    public void addMessage(String message) {
        synchronized (lock) {
            messages.addLast(message);
        }

        synchronized (lock2) {
            lock2.notifyAll();
        }
    }

    private int getMessageCounter() {
        synchronized (lock) {
            return messages.size();
        }
    }

    @Override
    public void run() {
        while(true) {
            String message = "";
            int numberInQueue = 0;

            synchronized (lock2) {
                while(getMessageCounter() == 0) {
                    try {
                        lock2.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

            synchronized (lock) {
                message = this.getMessage();
                numberInQueue = this.getMessageCounter();
            }

            System.out.printf("\r%10d: %5d: %s", lineCounter, numberInQueue, message);

            if(lineCounter % 100_000 == 0) {
                System.out.printf("%n");
            }
            lineCounter++;
        }
    }
}

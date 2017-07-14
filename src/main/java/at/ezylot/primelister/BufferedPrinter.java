package at.ezylot.primelister;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

public class BufferedPrinter implements Runnable {

    private final PrintStream out;

    private final Object lock = new Object();
    private final Object lock2 = new Object();

    private Deque<PrimeMessage> messages = new ArrayDeque<>();
    private long lineCounter = 1;

    public BufferedPrinter(OutputStream out) {
        this.out = new PrintStream(out);
    }

    private PrimeMessage getMessage() {
        synchronized (lock) {
            return messages.removeFirst();
        }
    }

    public void addMessage(PrimeMessage message) {
        synchronized (lock2) {
            synchronized (lock) {
                messages.addLast(message);
            }
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
        String message;
        int numberInQueue;
        while(true) {
            if(Thread.currentThread().isInterrupted()) {
                return;
            }

            synchronized (lock2) {
                while(getMessageCounter() == 0) {
                    try {
                        lock2.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                }
            }

            synchronized (lock) {
                message = this.getMessage().toString();
                numberInQueue = this.getMessageCounter();
            }

            out.printf("\r%10d: %5d: %s", lineCounter, numberInQueue, message);

            if(lineCounter % 100_000 == 0) {
                out.printf("%n");
            }
            lineCounter++;
        }
    }
}

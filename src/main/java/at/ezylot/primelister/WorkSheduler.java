package at.ezylot.primelister;

import at.ezylot.primelister.primechecker.LongPrimeChecker;
import at.ezylot.primelister.primechecker.PrimeChecker;
import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkSheduler {

    private long startTime = 0;
    private Integer threads = null;

    private List<PrimeChecker> workers = new ArrayList<>();
    private BufferedPrinter bufferedPrinter;

    private Set<BigInteger> primes = new HashSet<>();
    private BigInteger lastChecked = BigInteger.valueOf(3);

    public WorkSheduler(int threads) {
        this.threads = threads;
    }

    @Autowired
    protected void setBufferedPrinter(BufferedPrinter bufferedPrinter) {
        this.bufferedPrinter = bufferedPrinter;
    }


    public void startBlocking() throws InterruptedException {
        int thr = MoreObjects.firstNonNull(this.threads, Runtime.getRuntime().availableProcessors());
        ExecutorService workerExecutor = Executors.newFixedThreadPool(thr);

        for (int i = 0; i < thr; i++) {
            // Can be replaced with 'BigIntegerPrimeChecker'
            PrimeChecker checker = new LongPrimeChecker(this);
            this.workers.add(checker);
        }

        startTime = System.currentTimeMillis();
        for (PrimeChecker checker: this.workers) {
            workerExecutor.submit(checker);
        }

        Executors.newSingleThreadExecutor().submit(bufferedPrinter);
        this.isPrime(BigInteger.valueOf(2));
        this.isPrime(BigInteger.valueOf(3));
        workerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }


    public synchronized void isPrime(BigInteger prime) {
        primes.add(prime);

        long milliseconds = System.currentTimeMillis() - startTime + 1;
        assert milliseconds > 0;

        bufferedPrinter.addMessage(new PrimeMessage(
            prime.toString(),
            primes.size(),
            milliseconds
        ));
    }

    public synchronized Set<BigInteger> getPrimes() {
        return primes;
    }

    public synchronized BigInteger getNumberToWorkOn() {
        lastChecked = lastChecked.add(BigInteger.valueOf(2));
        return lastChecked;
    }

    public int getThreadCount(){
        return MoreObjects.firstNonNull(this.threads, Runtime.getRuntime().availableProcessors());
    }



}

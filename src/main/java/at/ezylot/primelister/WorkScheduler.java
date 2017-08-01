package at.ezylot.primelister;

import at.ezylot.primelister.primechecker.LongPrimeChecker;
import at.ezylot.primelister.primechecker.PrimeChecker;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkScheduler {

    private static final Logger logger = LoggerFactory.getLogger(WorkScheduler.class.getName());

    private long startTime = 0;
    private Integer threads = null;

    private List<PrimeChecker> workers = new ArrayList<>();
    private BufferedPrinter bufferedPrinter;

    private Set<BigInteger> primes = new HashSet<>();
    private BigInteger lastChecked = BigInteger.valueOf(3);

    public WorkScheduler(int threads) {
        this.threads = threads;
    }

    @Autowired
    protected void setBufferedPrinter(BufferedPrinter bufferedPrinter) {
        this.bufferedPrinter = bufferedPrinter;
    }


    public void startBlocking() throws InterruptedException {
        int thr = MoreObjects.firstNonNull(this.threads, Runtime.getRuntime().availableProcessors());
        logger.info("Starting the search with {} threads", thr);
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
        assert prime.signum() == 1;
        assert !prime.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO);

        logger.trace("Found a new prime: {}", prime.toString());

        primes.add(prime);

        long milliseconds = System.currentTimeMillis() - startTime + 1;
        assert milliseconds > 0;

        logger.trace("Sending this prime to the buffered printer");
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

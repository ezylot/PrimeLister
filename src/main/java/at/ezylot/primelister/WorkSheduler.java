package at.ezylot.primelister;

import at.ezylot.primelister.primechecker.BigIntegerPrimeChecker;
import at.ezylot.primelister.primechecker.LongPrimeChecker;
import at.ezylot.primelister.primechecker.PrimeChecker;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkSheduler {

    long startTime = 0;

    private List<PrimeChecker> workers = new ArrayList<>();
    private ExecutorService executerService;

    BufferedPrinter bufferedPrinter = new BufferedPrinter(System.out);

    private Set<BigInteger> primes = new HashSet<>();
    private BigInteger lastChecked = BigInteger.valueOf(3);

    public WorkSheduler(int threads) {
        Assert.isTrue(threads > 0, "Can not use 0 or less threads");
        executerService = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            // Can be replaced with 'BigIntegerPrimeChecker'
            PrimeChecker checker = new LongPrimeChecker(this);
            workers.add(checker);
        }
    }

    public void startBlocking() throws InterruptedException {
        startTime = System.currentTimeMillis();
        for (PrimeChecker checker: workers) {
            executerService.submit(checker);
        }

        Executors.newSingleThreadExecutor().submit(bufferedPrinter);
        this.isPrime(BigInteger.valueOf(2));
        this.isPrime(BigInteger.valueOf(3));
        executerService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
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

    public int getAmountOfWorkers(){
        return workers.size();
    }



}

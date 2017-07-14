package at.ezylot.primelister;

import org.springframework.util.Assert;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkSheduler {

    long startTime = 0;

    private List<NumberChecker> workers = new ArrayList<>();
    private ExecutorService executerService;

    BufferedPrinter bufferedPrinter = new BufferedPrinter(System.out);

    private Set<BigInteger> primes = new HashSet<>();
    private Map<BigInteger, LocalDateTime> currentlyWorkingOn = new HashMap<>();
    private BigInteger lastChecked = BigInteger.valueOf(3);

    public WorkSheduler(int threads) {
        Assert.isTrue(threads > 0, "Can not use 0 or less threads");
        executerService = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            NumberChecker checker = new NumberChecker(this);
            workers.add(checker);
        }
    }

    public void startBlocking() throws InterruptedException {
        startTime = System.currentTimeMillis();
        for (NumberChecker checker: workers) {
            executerService.submit(checker);
        }

        Executors.newSingleThreadExecutor().submit(bufferedPrinter);
        this.isPrime(BigInteger.valueOf(2));
        this.isPrime(BigInteger.valueOf(3));
        executerService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }


    public synchronized void isPrime(BigInteger prime) {
        primes.add(prime);
        currentlyWorkingOn.remove(prime);

        long milliseconds = System.currentTimeMillis() - startTime + 1;
        assert milliseconds > 0;

        double pps = ((double)primes.size() / (double)milliseconds) * 1000.0 ;
        assert pps > 0;

        bufferedPrinter.addMessage(new PrimeMessage(milliseconds/1000, prime.toString(), pps));

    }

    public synchronized Set<BigInteger> getPrimes() {
        return primes;
    }

    public synchronized void isNonPrime(BigInteger nonPrime) {
        currentlyWorkingOn.remove(nonPrime);
    }


    public synchronized BigInteger getNumberToWorkOn() {
        lastChecked = lastChecked.add(BigInteger.valueOf(2));
        return lastChecked;
    }

    public int getAmountOfWorkers(){
        return workers.size();
    }



}

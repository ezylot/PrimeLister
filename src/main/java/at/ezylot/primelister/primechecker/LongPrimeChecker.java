package at.ezylot.primelister.primechecker;

import at.ezylot.primelister.WorkScheduler;

import java.math.BigInteger;

public class LongPrimeChecker implements PrimeChecker {

    private final WorkScheduler scheduler;

    public LongPrimeChecker(WorkScheduler scheduler) {
        this.scheduler = scheduler;
    }

    private boolean checkPrime(long number) {
        long squareRoot;

        squareRoot = (long) Math.sqrt(number);

        if(number % 2 == 0) {
            return false;
        }
        for (long check = 3; check <= squareRoot; check += 2) {
            if(number % check == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void run() {
        while(true) {
            if(Thread.currentThread().isInterrupted()) {
                return;
            }

            long number = scheduler.getNumberToWorkOn().longValue();
            if(number == -1) {
                return;
            }
            if(checkPrime(number)) {
                scheduler.isPrime(BigInteger.valueOf(number));
            }
        }
    }
}

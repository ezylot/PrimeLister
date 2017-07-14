package at.ezylot.primelister.primechecker;

import at.ezylot.primelister.WorkSheduler;

import java.math.BigInteger;

public class LongPrimeChecker implements PrimeChecker {

    private final WorkSheduler sheduler;

    public LongPrimeChecker(WorkSheduler sheduler) {
        this.sheduler = sheduler;
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

            long number = sheduler.getNumberToWorkOn().longValue();
            if(checkPrime(number)) {
                sheduler.isPrime(BigInteger.valueOf(number));
            }
        }
    }
}

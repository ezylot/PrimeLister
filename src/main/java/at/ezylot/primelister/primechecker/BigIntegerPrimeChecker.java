package at.ezylot.primelister.primechecker;

import at.ezylot.primelister.WorkScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigInteger;

public class BigIntegerPrimeChecker implements PrimeChecker {

    private final WorkScheduler scheduler;
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger THREE = BigInteger.valueOf(3);

    @Autowired
    public BigIntegerPrimeChecker(WorkScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        BigInteger number;
        BigInteger squareRoot;
        boolean found;
        while(true) {
            if(Thread.currentThread().isInterrupted()) {
                return;
            }

            number = scheduler.getNumberToWorkOn();
            if(number.equals(BigInteger.valueOf(-1))) {
                return;
            }

            squareRoot = sqrt(number);
            found = false;

            if(number.remainder(TWO).equals(BigInteger.ZERO)) {
                found = true;
            }


            for (BigInteger check = THREE; check.compareTo(squareRoot) <= 0; check = check.add(TWO)) {
                if(number.remainder(check).equals(BigInteger.ZERO)) {
                    found = true;
                    break;
                }
            }


            if(!found) {
                scheduler.isPrime(number);
            }
        }
    }

    @Cacheable("squareroots")
    public BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }
}

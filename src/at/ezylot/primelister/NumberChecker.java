package at.ezylot.primelister;

import java.math.BigInteger;

public class NumberChecker implements Runnable {

    WorkSheduler sheduler;
    BigInteger TWO = BigInteger.valueOf(2);
    BigInteger THREE = BigInteger.valueOf(3);



    public NumberChecker(WorkSheduler sheduler) {
        this.sheduler = sheduler;
    }

    @Override
    public void run() {
        BigInteger number;
        BigInteger squareRoot;
        boolean found;
        while(true) {
            if(false) return;
            number = sheduler.getNumberToWorkOn();

            squareRoot = sqrt(number);
            found = false;

            if(number.remainder(TWO) == BigInteger.ZERO) {
                sheduler.isNonPrime(number);
                found = true;
            }


            for (BigInteger check = THREE; check.compareTo(squareRoot) <= 0; check = check.add(TWO)) {
                if(number.remainder(check) == BigInteger.ZERO) {
                    sheduler.isNonPrime(number);
                    found = true;
                    break;
                }
            }


            if(!found) {
                sheduler.isPrime(number);
            }
        }
    }

    private BigInteger sqrt(BigInteger n) {
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

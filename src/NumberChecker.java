import java.math.BigInteger;
import java.util.Set;

public class NumberChecker implements Runnable {

    WorkSheduler sheduler;

    public NumberChecker(WorkSheduler sheduler) {
        this.sheduler = sheduler;
    }

    @Override
    public void run() {
        while(true) {
            BigInteger number = sheduler.getNumberToWorkOn();
            if(number.equals(BigInteger.ZERO)) {
                return;
            }

            BigInteger squareRoot = sqrt(number);
            Boolean found = false;

            for (BigInteger check = BigInteger.valueOf(2); !found && check.compareTo(squareRoot) <= 0; check = check.add(BigInteger.ONE)) {
                if(number.mod(check).equals(BigInteger.ZERO)) {
                    sheduler.isNonPrime(number);
                    found = true;
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

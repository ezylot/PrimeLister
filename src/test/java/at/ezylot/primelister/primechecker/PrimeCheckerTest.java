package at.ezylot.primelister.primechecker;


import at.ezylot.primelister.WorkSheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.math.BigInteger;

import static org.mockito.Mockito.*;

public class PrimeCheckerTest {

    private WorkSheduler sheduler;

    @Before
    public void setup() {
        sheduler = mock(WorkSheduler.class);
        when(sheduler.getNumberToWorkOn())
            .thenReturn(
                BigInteger.valueOf(3),
                BigInteger.valueOf(4),
                BigInteger.valueOf(5),
                BigInteger.valueOf(6),
                BigInteger.valueOf(7),
                BigInteger.valueOf(8),
                BigInteger.valueOf(9),
                BigInteger.valueOf(10),
                BigInteger.valueOf(11),
                BigInteger.valueOf(-1)
            );
    }

    @Test
    public void checkLongPrimes() {
        InOrder order = inOrder(sheduler);

        LongPrimeChecker checker = new LongPrimeChecker(this.sheduler);
        checker.run();

        order.verify(sheduler).isPrime(BigInteger.valueOf(3));
        order.verify(sheduler).isPrime(BigInteger.valueOf(5));
        order.verify(sheduler).isPrime(BigInteger.valueOf(7));
        order.verify(sheduler).isPrime(BigInteger.valueOf(11));
    }

    @Test
    public void checkBigIntegerPrimes() {
        InOrder order = inOrder(sheduler);

        BigIntegerPrimeChecker checker = new BigIntegerPrimeChecker(this.sheduler);
        checker.run();

        order.verify(sheduler).isPrime(BigInteger.valueOf(3));
        order.verify(sheduler).isPrime(BigInteger.valueOf(5));
        order.verify(sheduler).isPrime(BigInteger.valueOf(7));
        order.verify(sheduler).isPrime(BigInteger.valueOf(11));
    }

}
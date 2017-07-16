package at.ezylot.primelister.primechecker;


import at.ezylot.primelister.WorkScheduler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.math.BigInteger;

import static org.mockito.Mockito.*;

public class PrimeCheckerTest {

    private WorkScheduler scheduler;

    @Before
    public void setup() {
        scheduler = mock(WorkScheduler.class);
        when(scheduler.getNumberToWorkOn())
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
        InOrder order = inOrder(scheduler);

        LongPrimeChecker checker = new LongPrimeChecker(this.scheduler);
        checker.run();

        order.verify(scheduler).isPrime(BigInteger.valueOf(3));
        order.verify(scheduler).isPrime(BigInteger.valueOf(5));
        order.verify(scheduler).isPrime(BigInteger.valueOf(7));
        order.verify(scheduler).isPrime(BigInteger.valueOf(11));
    }

    @Test
    public void checkBigIntegerPrimes() {
        InOrder order = inOrder(scheduler);

        BigIntegerPrimeChecker checker = new BigIntegerPrimeChecker(this.scheduler);
        checker.run();

        order.verify(scheduler).isPrime(BigInteger.valueOf(3));
        order.verify(scheduler).isPrime(BigInteger.valueOf(5));
        order.verify(scheduler).isPrime(BigInteger.valueOf(7));
        order.verify(scheduler).isPrime(BigInteger.valueOf(11));
    }

}
package at.ezylot.primelister;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

public class WorkSchedulerTest {

    private WorkScheduler scheduler;

    @Before
    public void setUp() {
        scheduler = new WorkScheduler(1);
    }

    @Test
    public void onlyOddNumbers() {
        Assert.assertEquals(1, scheduler.getThreadCount());
        Assert.assertEquals(0, scheduler.getPrimes().size());

        BigInteger two = BigInteger.valueOf(2);
        Assert.assertTrue(scheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(scheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(scheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(scheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(scheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
    }

}
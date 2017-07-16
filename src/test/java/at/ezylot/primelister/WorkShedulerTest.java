package at.ezylot.primelister;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

public class WorkShedulerTest {

    private WorkSheduler sheduler;

    @Before
    public void setUp() {
        sheduler = new WorkSheduler(1);
    }

    @Test
    public void onlyOddNumbers() {
        Assert.assertEquals(1, sheduler.getThreadCount());
        Assert.assertEquals(0, sheduler.getPrimes().size());

        BigInteger two = BigInteger.valueOf(2);
        Assert.assertTrue(sheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(sheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(sheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(sheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
        Assert.assertTrue(sheduler.getNumberToWorkOn().mod(two).equals(BigInteger.ONE));
    }

}
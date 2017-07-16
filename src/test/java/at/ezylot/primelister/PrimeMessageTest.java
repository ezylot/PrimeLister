package at.ezylot.primelister;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class PrimeMessageTest {

    @Test
    public void testToString() {

        String basestring = "%30s - last prime found after: %2dh %2dm %2ds WITH a pps of %5.4f";

        PrimeMessage message1 = new PrimeMessage("1", 1, 1000);
        PrimeMessage message2 = new PrimeMessage("2", 1, 2000);
        PrimeMessage message3 = new PrimeMessage("3", 1, 1);
        PrimeMessage message4 = new PrimeMessage("4", 10, 1000);
        PrimeMessage message5 = new PrimeMessage("5", 1_000_000, 1_000_000_000);

        PrimeMessage exitMessage = new PrimeMessage(null, 0, 0);


        Assert.assertEquals(PrimeMessage.MessageType.MESSAGE, message1.getMessageType());
        Assert.assertEquals(PrimeMessage.MessageType.MESSAGE, message2.getMessageType());
        Assert.assertEquals(PrimeMessage.MessageType.MESSAGE, message3.getMessageType());
        Assert.assertEquals(PrimeMessage.MessageType.MESSAGE, message4.getMessageType());
        Assert.assertEquals(PrimeMessage.MessageType.MESSAGE, message5.getMessageType());

        Assert.assertEquals(PrimeMessage.MessageType.EXIT, exitMessage.getMessageType());



        Assert.assertEquals(String.format(Locale.ENGLISH, basestring, "1", 0, 0, 1, 1.0), message1.toString());
        Assert.assertEquals(String.format(Locale.ENGLISH, basestring, "2", 0, 0, 2, 0.5), message2.toString());
        Assert.assertEquals(String.format(Locale.ENGLISH, basestring, "3", 0, 0, 0, 1000.0), message3.toString());
        Assert.assertEquals(String.format(Locale.ENGLISH, basestring, "4", 0, 0, 1, 10.0), message4.toString());
        Assert.assertEquals(String.format(Locale.ENGLISH, basestring, "5", 277, 46, 40, 1.0), message5.toString());

        Assert.assertEquals("EXIT", exitMessage.toString());
    }

}
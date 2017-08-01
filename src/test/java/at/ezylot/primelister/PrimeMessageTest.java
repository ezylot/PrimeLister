package at.ezylot.primelister;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

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


        assertThat(message1.getMessageType()).isEqualTo(PrimeMessage.MessageType.MESSAGE);
        assertThat(message2.getMessageType()).isEqualTo(PrimeMessage.MessageType.MESSAGE);
        assertThat(message3.getMessageType()).isEqualTo(PrimeMessage.MessageType.MESSAGE);
        assertThat(message4.getMessageType()).isEqualTo(PrimeMessage.MessageType.MESSAGE);
        assertThat(message5.getMessageType()).isEqualTo(PrimeMessage.MessageType.MESSAGE);

        assertThat(exitMessage.getMessageType()).isEqualTo(PrimeMessage.MessageType.EXIT);


        assertThat( message1.toString()).isEqualTo(String.format(Locale.ENGLISH, basestring, "1", 0, 0, 1, 1.0));
        assertThat( message2.toString()).isEqualTo(String.format(Locale.ENGLISH, basestring, "2", 0, 0, 2, 0.5));
        assertThat( message3.toString()).isEqualTo(String.format(Locale.ENGLISH, basestring, "3", 0, 0, 0, 1000.0));
        assertThat( message4.toString()).isEqualTo(String.format(Locale.ENGLISH, basestring, "4", 0, 0, 1, 10.0));
        assertThat( message5.toString()).isEqualTo(String.format(Locale.ENGLISH, basestring, "5", 277, 46, 40, 1.0));

        assertThat(exitMessage.toString()).isEqualTo("EXIT");
    }
}

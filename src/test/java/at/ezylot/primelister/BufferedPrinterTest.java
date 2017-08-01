package at.ezylot.primelister;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class BufferedPrinterTest {

    ByteArrayOutputStream byteOutputStream;
    BufferedPrinter bufferedPrinter;

    @Before
    public void setup() {
        byteOutputStream = new ByteArrayOutputStream();
        bufferedPrinter = new BufferedPrinter(byteOutputStream);
        bufferedPrinter.setSkipPrint(false);
    }

    @Test
    public void testWrite() throws InterruptedException {
        PrimeMessage message1 = new PrimeMessage("1", 1, 1);
        PrimeMessage message2 = new PrimeMessage("2", 2, 1000);
        PrimeMessage message3 = new PrimeMessage("3", 4, 2000);

        PrimeMessage exitMessage = new PrimeMessage(null, 0, 0);

        bufferedPrinter.addMessage(message1);
        bufferedPrinter.addMessage(message2);
        bufferedPrinter.addMessage(message3);

        bufferedPrinter.addMessage(exitMessage);

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(bufferedPrinter);
        service.shutdown();
        if(!service.awaitTermination(10, TimeUnit.SECONDS)) {
            fail("Buffered Printer did not stop on exit signal");
        }

        String output = new String(byteOutputStream.toByteArray());
        String[] lines = output.split("\r");

        assertThat(lines.length).isEqualTo(5);

        assertThat(lines[2].trim()).startsWith("1:");
        assertThat(lines[2]).contains("last prime found after:  0h  0m  0s WITH a pps of 1000.0");

        assertThat(lines[3].trim()).startsWith("2:");
        assertThat(lines[3]).contains("last prime found after:  0h  0m  1s WITH a pps of 2.0");

        assertThat(lines[4].trim()).startsWith("3:");
        assertThat(lines[4]).contains("last prime found after:  0h  0m  2s WITH a pps of 2.0");
    }

}
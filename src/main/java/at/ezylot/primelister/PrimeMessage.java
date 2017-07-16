package at.ezylot.primelister;

import java.util.Locale;

public class PrimeMessage {

    enum MessageType {
        MESSAGE,
        EXIT
    }

    private final long millis;
    private final String primeNumber;
    private final long counter;
    private MessageType messageType = MessageType.MESSAGE;

    public PrimeMessage(String primeNumber, long alreadyFound, long millis) {
        if(primeNumber == null) {
            this.messageType = MessageType.EXIT;
        }
        this.millis = millis;
        this.primeNumber = primeNumber;
        this.counter = alreadyFound;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        if (messageType == MessageType.EXIT) {
            return "EXIT";
        }

        double pps = ((double)counter / (double)millis) * 1000.0 ;
        long seconds = millis / 1000;

        return String.format(Locale.ENGLISH,"%30s - last prime found after: %2dh %2dm %2ds WITH a pps of %5.4f",
            primeNumber,
            seconds/3600,
            (seconds%3600)/60,
            seconds%60,
            pps
        );
    }
}

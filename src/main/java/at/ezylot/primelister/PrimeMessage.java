package at.ezylot.primelister;

public class PrimeMessage {
    private final long millis;
    private final String primeNumber;
    private final long counter;

    public PrimeMessage(String primeNumber, long alreadyFound, long millis) {
        this.millis = millis;
        this.primeNumber = primeNumber;
        this.counter = alreadyFound;
    }

    @Override
    public String toString() {
        double pps = ((double)counter / (double)millis) * 1000.0 ;
        long seconds = millis / 1000;

        return String.format("%30s - last prime found after: %2dh %2dm %2ds WITH a pps of %f",
            primeNumber,
            seconds/3600,
            (seconds%3600)/60,
            seconds%60,
            pps
        );
    }
}

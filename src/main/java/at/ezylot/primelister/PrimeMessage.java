package at.ezylot.primelister;

public class PrimeMessage {
    private final long seconds;
    private final String primeNumber;
    private final double pps;

    public PrimeMessage(long seconds, String primeNumber, double pps) {
        this.seconds = seconds;
        this.primeNumber = primeNumber;
        this.pps = pps;
    }

    @Override
    public String toString() {
         return String.format("%30s - last prime found after: %2dh %2dm %2ds WITH a pps of %f",
             primeNumber,
             seconds/3600,
             (seconds%3600)/60,
             seconds%60,
             pps
         );
    }
}

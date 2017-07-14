package at.ezylot.primelister;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        WorkSheduler sheduler = new WorkSheduler(4);
        sheduler.startBlocking();
    }
}

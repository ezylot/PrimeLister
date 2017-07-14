import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        WorkSheduler sheduler = new WorkSheduler(4);
        try {
            sheduler.startBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

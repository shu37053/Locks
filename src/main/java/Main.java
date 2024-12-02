import managers.BarrierTest;
import managers.ReadWriteLockManager;

public class Main {
    public static void main(String[] args) {
        BarrierTest test = new BarrierTest(3);
        for (int i = 0; i < 12; i++) {
            test.addThread();
        }
    }
}

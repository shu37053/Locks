package managers;

import locks.Barrier;

import java.util.Random;

public class BarrierTest {
    private final Random random;
    private final Barrier barrier;
    private int currCount;

    public BarrierTest(final int count) {
        random = new Random();
        barrier = new Barrier(count);
        currCount = 1;
    }

    public void addThread() {
        new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                long time = getSleepTime();
//                Thread.sleep(time);
//                System.out.printf("Thread: %s thread sleeping for time %s%n", name, time);
                barrier.enter();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, String.valueOf(currCount++)).start();
    }

    private long getSleepTime() {
        return random.nextInt(50) * 100;
    }
}

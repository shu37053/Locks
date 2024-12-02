package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private final int maxCount;
    private int currentCount;
    private int released;
    private ReentrantLock lock;
    private Condition barrierAlreadyFull;
    private Condition waitForBarrierToBeFilled;

    public Barrier(int maxCount) {
        this.maxCount = maxCount;
        this.currentCount = 0;
        released = 0;
        lock = new ReentrantLock();
        barrierAlreadyFull = lock.newCondition();
        waitForBarrierToBeFilled = lock.newCondition();
    }

    public void enter() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            while (isBarrierAlreadyFull()) {
                System.out.printf("Thread: %s Barrier Already Full%n", name);
                barrierAlreadyFull.await();
                System.out.printf("Thread: %s Barrier Already Full - continue%n", name);
            }
            currentCount++;
            if (currentCount == maxCount) {
                released = 0;
                waitForBarrierToBeFilled.signalAll();
                System.out.printf("Thread: %s Barrier Full - restarting%n", name);
            } else {
                while (currentCount < maxCount) {
                    System.out.printf("Thread: %s Waiting barrier full%n", name);
                    waitForBarrierToBeFilled.await();
                }

            }
            released++;
            System.out.printf("Thread: %s Released from barrier%n", name);
            if (released == currentCount) {
                currentCount = 0;
                barrierAlreadyFull.signalAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            ;
        }
    }

    private boolean isBarrierAlreadyFull() {
        return maxCount == currentCount;
    }
}

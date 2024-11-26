package managers;

import exception.MaxTokenPresent;
import locks.ReadWriteLock;

import java.util.Random;

public class ReadWriteLockManager {
    private final ReadWriteLock lock;
    private int threadName;
    private Random random;

    public ReadWriteLockManager() {
        lock = new ReadWriteLock();
        threadName = 0;
        random = new Random();
    }

    public void testReadLock() {
        Thread thread = new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.printf("From : %s trying read lock%n", name);
                lock.acquireReadLock();
                long sleepTime = getSleepTime();
                System.out.printf("Thread: %s. ReadLock acquired. Waiting = %f%n", name, (1.0 * sleepTime)/1000);
                Thread.sleep(sleepTime);
                System.out.printf("Thread: %s. ReadLock released%n", name);
                lock.releaseReadLock();
                System.out.printf("From : %s - Resting%n", name);
            } catch (InterruptedException e) {
                System.out.println("Thread interuppted");
            } catch (MaxTokenPresent e) {
                throw new RuntimeException(e);
            }
        }, String.valueOf(threadName++));
        thread.start();
    }

    public void testWriteLock() {
        Thread thread = new Thread(() -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.printf("From : %s trying write lock%n", name);
                lock.acquireWriteLock();
                long sleepTime = getSleepTime();
                System.out.printf("Thread: %s. WriteLock acquired. Waiting = %f%n", name, (1.0 * sleepTime)/1000);
                Thread.sleep(sleepTime);
                System.out.printf("Thread: %s. WriteLock released%n", name);
                lock.releaseWriteLock();
                System.out.printf("From : %s - Resting%n", name);
            } catch (InterruptedException e) {
                System.out.println("Thread interuppted");
            }
        }, String.valueOf(threadName++));
        thread.start();
    }

    private long getSleepTime() {
        long a = random.nextInt(20)*100;
        return a;
    }
}

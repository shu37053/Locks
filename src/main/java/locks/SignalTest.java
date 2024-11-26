package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SignalTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        for(int i=0;i<5;i++) {
            new Thread(()->{
                try {
                    lock.lock();
                    String name = Thread.currentThread().getName();
                    System.out.printf("Thread: %s waiting on condition%n", name);
                    condition.await();
                    System.out.printf("Thread: %s - restarting%n", name);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }, String.valueOf(i)).start();
        }
        Thread.sleep(5000);
        lock.lock();
        condition.signalAll();
        lock.unlock();
    }
}

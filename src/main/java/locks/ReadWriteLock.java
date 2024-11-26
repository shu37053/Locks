package locks;

import exception.MaxTokenPresent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLock {
    private int readCount;
    private boolean writeLockAcquired;
    private final ReentrantLock lock;
    private final Condition writeLockCondition;
    private final Condition readLockCondition;

    public ReadWriteLock() {
        readCount = 0;
        writeLockAcquired = false;
        lock = new ReentrantLock();
        writeLockCondition = lock.newCondition();
        readLockCondition = lock.newCondition();
    }

    public void acquireReadLock() {
        String name = Thread.currentThread().getName();
        try {
            lock.lock();
            while (writeLockAcquired) {
                writeLockCondition.await();
            }
            readCount++;
            System.out.printf("Thread: %s current reader count = %s%n", name, readCount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void releaseReadLock() throws MaxTokenPresent {
        try {
            lock.lock();
            readCount--;
            System.out.printf("Thread: %s current reader count = %s%n", Thread.currentThread().getName(), readCount);
            if (readCount == 0) {
                readLockCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void acquireWriteLock() {
        try {
            lock.lock();
            while (readCount > 0) {
                readLockCondition.await();
            }
            while (writeLockAcquired) {
                writeLockCondition.await();
            }
            writeLockAcquired = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void releaseWriteLock() {
        try {
            lock.lock();
            writeLockAcquired = false;
            writeLockCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

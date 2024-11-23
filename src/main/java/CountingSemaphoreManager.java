import exception.MaxTokenPresent;

public class CountingSemaphoreManager {
    private final CountingSemaphore semaphore;

    private int threadId;

    CountingSemaphoreManager(int count) {
        semaphore = new CountingSemaphore(count);
        threadId = 1;
    }

    public void startThread(final long waitTime) {
        Thread thread = new Thread(() -> {
            try {
                semaphore.acquireLock();
                System.out.printf("Thread %s acquired Lock AND sleeping for %s%n", Thread.currentThread().getName(), waitTime);
                Thread.sleep(waitTime);
                semaphore.releaseLock();
                System.out.printf("Thread %s releasing lock %n", Thread.currentThread().getName());
            } catch (MaxTokenPresent | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, String.valueOf(threadId++));
        thread.start();
    }
}

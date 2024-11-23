import exception.MaxTokenPresent;
import exception.NoAvailableToken;

public class CountingSemaphore {
    private final int maxTokenCount;
    private int currentTokenCount;

    public CountingSemaphore(int maxTokenCount) {
        this.maxTokenCount = maxTokenCount;
        currentTokenCount = 0;
    }

    public boolean acquireLock() {
        synchronized (this) {
            while (true) {
                try {
                    decrementToken();
                    return true;
                } catch (NoAvailableToken e) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Waiting thread interrupted");
                    }
                }
            }
        }
    }

    public synchronized boolean releaseLock() throws MaxTokenPresent {
        try {
            incrementToken();
            notifyAll();
            return true;
        } catch (MaxTokenPresent e) {
            System.out.println("Token call not allowed"+e.getMessage());
            throw e;
        }
    }

    private void incrementToken() throws MaxTokenPresent {
        if(currentTokenCount == 0) {
            throw new MaxTokenPresent();
        }
        currentTokenCount--;
    }

    private void decrementToken() throws NoAvailableToken {
        if(maxTokenCount == currentTokenCount) {
            throw new NoAvailableToken();
        }
        currentTokenCount++;
    }
}

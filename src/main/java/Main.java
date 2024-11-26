import managers.ReadWriteLockManager;

public class Main {
    public static void main(String[] args) {
        ReadWriteLockManager manager = new ReadWriteLockManager();
        for (int i = 0; i <= 10; i++) {
            if(i%3 == 0) {
                manager.testWriteLock();
            } else {
                manager.testReadLock();
            }
        }
    }
}

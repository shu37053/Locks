import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        CountingSemaphoreManager manager = new CountingSemaphoreManager(3);
        for(int i=0;i<20;i++) {
            long waitTime = random.nextInt(50) * 1000;
            manager.startThread(waitTime);
        }
    }
}

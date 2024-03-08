package thread;

import java.util.concurrent.Executors;

public class Virtual {
    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            System.out.println("From the runnable");
        };

        Thread t = Thread.ofVirtual().start(r);
        t.join();
        Executors.newVirtualThreadPerTaskExecutor();
    }

}

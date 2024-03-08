import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumer {
    //multiple threads will execute the producer
    static class Producer implements Runnable {
        int num;
        ArrayBlockingQueue<Integer> queue;

        Producer(int num, ArrayBlockingQueue<Integer> queue) {
            this.num = num;
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 1; i < 1000; i++) {
                if(i % num == 0) {
                    try {
                        queue.put(i);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted while trying to add item in Q " + Thread.currentThread().getName());
                        break;
                    }
                }
            }
        }
        // add 1 to 100 numbers in the Queue
    }

    static class MultiplyingConsumer implements Runnable {

        ArrayBlockingQueue<Integer> queue;
        int num;

        MultiplyingConsumer(int num, ArrayBlockingQueue<Integer> queue) {
            this.num = num;
            this.queue = queue;
        }

        @Override
        public void run() {

            while(true) {
                int item = 0;
                try {
                    item = queue.take();
                } catch (InterruptedException e) {
                    System.out.println("Consumer thread interrupted while consuming");
                    throw new RuntimeException(e);
                }
                if(item < 0) {
                    System.out.println("Exiting by eating poison pill");
                    break;
                }
                System.out.printf("[%s] Item: %d and Result: %d%n", Thread.currentThread().getName(), item, num * item);
            }
        }
        // consume numbers from the queue and
    }


    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        Producer p1 = new Producer(3, queue);
        Producer p2 = new Producer(5, queue);
        Producer p3 = new Producer(16, queue);

        MultiplyingConsumer c1 = new MultiplyingConsumer(2, queue);

        Runnable tenTimesMultiplier = () -> {
            while(true) {
                int item = 0;
                try {
                    item = queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(item < 0) break;
                System.out.printf("[%s] TenTimes Item: %d and Result: %d%n", Thread.currentThread().getName(), item, 10 * item);
            }
        };

        System.out.println("Main thread terminated");

        // executor service
        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            executorService.submit(p1);
            executorService.submit(p2);
            executorService.submit(p3);
            executorService.submit(c1);
            executorService.submit(tenTimesMultiplier);

            boolean b = executorService.awaitTermination(5 * 1000, TimeUnit.MILLISECONDS);
            //queue.put(-1); queue.put(-1);
            executorService.shutdownNow();
        }


    }


}

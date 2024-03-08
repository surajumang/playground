import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadOrder {
    public static void main(String[] args) throws Exception {

        CountDownLatch latch2 = new CountDownLatch(1);
        CountDownLatch latch3 = new CountDownLatch(1);

        Thread t1 = new Thread(()-> {
            try{
                System.out.println("From Thread 1");
                latch2.countDown();
            }finally {
            }
        });

        Thread t2 = new Thread(()-> {
            try{
                latch2.await();
                System.out.println("From Thread 2");
                latch3.countDown();
            }catch (Exception e){
                System.out.println("Exception " + e);
            }
            finally {
            }
        });

        Thread t3 = new Thread(()-> {
            try{
                latch3.await();
                System.out.println("From Thread 3");
            }catch (Exception e){
                System.out.println("Exception " + e);
            }
            finally {
            }
        });

        t3.start();
        t2.start();
        t1.start();

        t3.join();
        System.out.println("FInished main thread");
    }


}

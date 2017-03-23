package concurrency;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ray on 2017/3/23.
 */
public class SimpleDaemons implements Runnable {
    @Override
    public void run() {

        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + "  " + this);
            }
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    @Test
    public void main() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons starts");
        TimeUnit.MILLISECONDS.sleep(200);
    }

}

package concurrency;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ray on 2017/3/26.
 */
public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException{
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupting send to " + r.getClass().getName());
    }

    @Test
    public void main() throws InterruptedException {
//        test(new SleepBlocked());
//        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());

        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }

}

class SleepBlocked implements Runnable{
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable{
    private InputStream in;
    public IOBlocked(InputStream is){
        in = is;
    }

    @Override
    public void run() {
        System.out.println("Wait for read()");
        try {
            in.read();
        } catch (IOException e) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from blocked I/O");
            }
            else{
                throw new RuntimeException(e);
            }
        }
    }
}

class SynchronizedBlocked implements Runnable{
    public synchronized void f(){
        while (true){       //不释放锁
            Thread.yield();
        }
    }

    public SynchronizedBlocked(){
        new Thread(this::f).start();
    }

    @Override
    public void run() {
        System.out.println("Try to call f()");
        f();
        System.out.println("Exit SynchronizedBlock.run()");
    }
}













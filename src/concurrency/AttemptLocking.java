package concurrency;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ray on 2017/3/23.
 */
public class AttemptLocking {

    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();

        try {
            System.out.println("tryLock() :" + captured);
        }finally {
            if(captured) {
                lock.unlock();
            }
        }
    }

    public void timed(){
        boolean captured = false;
        try {
            //尝试2秒后离开
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if(captured){
                lock.unlock();
            }
        }

    }

    @Test
    public void main(){
        final AttemptLocking al = new AttemptLocking();
        al.untimed();
        al.timed();

        new Thread(){
            {setDaemon(true);}
            public void run(){
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();

        Thread.yield();
        al.untimed();
        al.timed();

    }



}

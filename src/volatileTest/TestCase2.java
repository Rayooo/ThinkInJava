package volatileTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create By Ray on 2017/4/28 10:22
 */
public class TestCase2 {

    public volatile int v = 0;

    public static final int threadCount = 10;

    public void increase() {
        v++;                                //++为非原子操作
    }

    public synchronized void increaseSync(){
        v++;
    }

    Lock lock = new ReentrantLock();

    public void increaseLock(){
        lock.lock();
        try {
            v++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
        TestCase2 testCase = new TestCase2();

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
//                    testCase.increase();
//                    testCase.increaseSync();
                    testCase.increaseLock();
                }
            }).start();
        }

        while (Thread.activeCount() > 1){
            Thread.yield();
        }

        System.out.println(testCase.v);
        //若采用 increase() 无法到达10000，测试为9364
        //采用increaseSync() 可到达10000
        //采用increaseLock() 可达到10000


    }

}

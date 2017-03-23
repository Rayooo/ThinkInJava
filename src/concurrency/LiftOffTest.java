package concurrency;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ray on 2017/3/23.
 */
public class LiftOffTest {

    @Test
    public void mainThread(){
        LiftOff liftOff = new LiftOff();
        liftOff.run();
    }

    @Test
    public void basicThreads(){
        Thread thread = new Thread(new LiftOff());
        thread.start();
        System.out.println("waiting for LiftOff");
    }

    @Test
    public void moreBasicThreads(){
        for(int i = 0;i < 5;++i){
            new Thread(new LiftOff()).run();
            System.out.println();
        }
        System.out.println("waiting for LiftOff");
    }

    //cachedThreadPool
    @Test
    public void cachedThreadPool(){
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0;i < 5; ++i){
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }

    //fixedThreadPool
    @Test
    public void fixedThreadPool(){
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for(int i = 0;i < 5; ++i){
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }

    //singleThreadPool
    @Test
    public void singleThreadPool(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(int i = 0;i < 5; ++i){
            exec.execute(new LiftOff());
        }
//        exec.shutdown();
    }

}

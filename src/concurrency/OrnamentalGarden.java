package concurrency;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ray on 2017/3/25.
 *  java 编程思想 P692
 */
public class OrnamentalGarden {
    @Test
    public void main() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }

        TimeUnit.SECONDS.sleep(3);      //这个控制了进入人的数量
        Entrance.cancel();
        exec.shutdown();

        if(!exec.awaitTermination(250,TimeUnit.MILLISECONDS)){
            System.out.println("Some tasks were not terminated");
        }
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances : " + Entrance.sumEntrances());

    }
}

class Count {
    private int count = 0;
    private Random rand = new Random(47);

    //****************************************************************************
    //如果移除了synchronized关键字会看到计数失败
    //移除后sum和Total计数不同
    public synchronized int increment(){
        int tmp = count;
        if(rand.nextBoolean()){
            Thread.yield();
        }
        return (count = ++tmp);
    }

    public synchronized int value(){
        return count;
    }
}

class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>();
    private int number = 0;
    //不需要sync关键字去读取
    private final int id;

    private static volatile boolean canceled = false;

    public static void cancel(){
        canceled = true;
    }

    public Entrance(int id){
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled){
            synchronized (this){
                ++number;
            }
            System.out.println(this + " Total: "+ count.increment());

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }

        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue(){
        return number;
    }

    @Override
    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount(){
        return count.value();
    }

    public static int sumEntrances(){
        int sum = 0;
        for(Entrance entrance : entrances){
            sum += entrance.getValue();
        }
        return sum;
    }

}








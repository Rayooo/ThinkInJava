package concurrency;

import org.junit.Test;

/**
 * Created by Ray on 2017/3/23.
 */
public class Joining {

    @Test
    public void main(){
        Sleeper sleeper = new Sleeper("Sleepy", 1500);
        Sleeper sleeper2 = new Sleeper("sleeper2", 1500);

        Joiner joiner = new Joiner("Joiner", sleeper);
        Joiner joiner2 = new Joiner("Joiner2", sleeper2);

        sleeper2.interrupt();

    }

}


class Sleeper extends Thread {
    private int duration;

    public Sleeper(String name, int duration) {
        super(name);
        this.duration = duration;
        start();
    }

    public void run(){
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was Interrupted ," + "isInterrupted() " + isInterrupted());
            return;
        }
        System.out.println(getName() + "has awakened");
    }

}

class Joiner extends Thread{
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run(){
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
    }

}
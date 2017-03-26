package concurrency;

import org.junit.Test;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ray on 2017/3/26.
 * 有问题
 */
public class Restaurant {
    Meal meal;
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);
    ExecutorService exec = Executors.newCachedThreadPool();

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    @Test
    public void main(){
        new Restaurant();
    }
    

}

class Meal{
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal " + orderNum;
    }
}

class WaitPerson implements Runnable{
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (restaurant.meal == null){
                        wait();
                    }
                }
                System.out.println("Wait person got " + restaurant.meal);

                synchronized (restaurant.chef){
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        }catch (InterruptedException e){
            System.out.println("WaitPerson Interrupted");
        }
    }
}

class Chef implements Runnable{
    private Restaurant restaurant;
    private int count = 0;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            synchronized (this){
                while (restaurant.meal != null){
                    wait();
                }

                ++count;
                if(count == 10){
                    System.out.println("Out of food ,closing");
                    restaurant.exec.shutdownNow();
                }
            }
            System.out.println("Order up");
            synchronized (restaurant.waitPerson){
                restaurant.meal = new Meal(count);
                restaurant.waitPerson.notifyAll();
            }
            TimeUnit.MILLISECONDS.sleep(100);

        } catch (InterruptedException e) {
            System.out.println("Chef Interrupted");
        }

    }
}










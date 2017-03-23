package concurrency;

/**
 * Created by Ray on 2017/3/23.
 */
public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff(){}

    public LiftOff(int countDown){
        this.countDown = countDown;
    }

    public String status(){
        return "#" + id + "(" + (countDown > 0? countDown : "LiftOff") + ")";
    }

    public void run(){
        while (countDown -- > 0){
            System.out.print(status());
            Thread.yield();
            //先检测当前是否有相同优先级的线程处于同可运行状态，如有，则把CPU的占有权交给次线程，
            // 否则继续运行原来的线程，所以yield()方法称为“退让”，它把运行机会让给了同等级的其他线程。
        }
    }

}

package volatileTest;

/**
 * Create by Ray on 2017/4/28 10:18
 */
public class TestCase1 {

    public static volatile int number;

    public static boolean isinited;

    public static void main(String[] args){
        new Thread(() -> {
            while (!isinited){
                Thread.yield();
            }
            System.out.println(number);
        }).start();
        number = 20;
        isinited = true;
    }

}

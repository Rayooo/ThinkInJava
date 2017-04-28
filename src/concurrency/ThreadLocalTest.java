package concurrency;

/**
 *  2017/4/28 19:54
 */

class BIncrement{
    ThreadLocal<Integer> num = ThreadLocal.withInitial(() -> 0);
    public int increase(){
        num.set(num.get() + 1);
        return num.get();
    }
    public void print(){
        for (int i = 0; i < 4; i++) {
            System.out.println(Thread.currentThread().getName() + " " + increase());
        }
    }
}

class AIncrement{
    int num = 0;
    public int increase(){
        num = num + 1;
        return num;
    }
    public void print(){
        for (int i = 0; i < 4; i++) {
            System.out.println(Thread.currentThread().getName() + " " + increase());
        }
    }
}

public class ThreadLocalTest {

    public static void main(String[] args){

        //A 与 B 的区别在于ThreadLocal，
        //在多线程中，对于同一个对象的访问和修改会造成冲突，而使用 ThreadLocal 创建的变量只能被当前线程使用，不会受到其他线程的干扰。
        //  num 在多线程共享下，读取-赋值-写入的操作引起了冲突。

//        AIncrement increment = new AIncrement();
        BIncrement increment = new BIncrement();

        for (int i = 0; i < 3; i++) {
            new Thread(increment::print).start();
        }
    }


}
//   A:   Thread-0 1
//        Thread-0 4
//        Thread-0 5
//        Thread-0 6
//        Thread-2 3
//        Thread-2 7
//        Thread-2 8
//        Thread-2 9
//        Thread-1 2
//        Thread-1 10
//        Thread-1 11
//        Thread-1 12

//  B:    Thread-0 1
//        Thread-0 2
//        Thread-0 3
//        Thread-2 1
//        Thread-2 2
//        Thread-1 1
//        Thread-1 2
//        Thread-1 3
//        Thread-1 4
//        Thread-2 3
//        Thread-2 4
//        Thread-0 4



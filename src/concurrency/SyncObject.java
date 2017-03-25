package concurrency;

import org.junit.Test;

/**
 * Created by Ray on 2017/3/25.
 */
public class SyncObject {

    @Test
    public void main(){
        final DualSynch ds = new DualSynch();

        new Thread(ds::f).start();
        ds.g();

    }

}

class DualSynch{

    private Object syncObject = new Object();

    public synchronized void f(){
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            Thread.yield();
        }
    }

    public void g(){
        synchronized (syncObject){
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }


}

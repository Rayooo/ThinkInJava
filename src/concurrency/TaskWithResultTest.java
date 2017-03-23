package concurrency;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Ray on 2017/3/23.
 */
public class TaskWithResultTest {

    @Test
    public void callableDemo(){
        // Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。
        // 必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。

        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> result = new ArrayList<>();
        for(int i = 0; i < 10; ++ i){
            result.add(exec.submit(new TaskWithResult(i)));
        }

        for(Future<String> fs : result){
            try {
                //isDone查看任务是否完成
                //get 会阻塞，直到上面完成
                System.out.println(fs.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                exec.shutdown();
            }
        }


    }

}

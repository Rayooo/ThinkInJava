package concurrency;

import java.util.concurrent.Callable;

/**
 * Created by Ray on 2017/3/23.
 */
public class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call(){
        return "result of TaskWithResult " + id;
    }

}

package io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ray on 2017/4/9.
 */

/*

文件加锁，它允许我们同步访问某个作为共享资源的文件，文件锁对其他的操作系统进程是可见的，
Java的文件加锁直接映射到了本地操作系统的加锁工具，见io/FileLocking.java。
调用tryLock()和lock()可以获得整个文件的FileLock，tryLock()是非阻塞式的，它设法获取锁，但是如果不能获得，
它将直接从方法调用返回，lock()是阻塞式的，它要阻塞进程直到锁可以获得，或是调用lock()的线程中断，
或者调用lock()的通道关闭。也可以指定参数使文件的一部分上锁

* */

public class FileLocking {
    @Test
    public void main() throws IOException, InterruptedException {
        FileOutputStream fos = new FileOutputStream("./src/io/FileLocking.java");
        FileLock fl = fos.getChannel().tryLock();
        if(fl != null){
            System.out.println("Locked File");
            TimeUnit.MILLISECONDS.sleep(100);
            fl.release();
            System.out.println("Released Lock");
        }
        fos.close();
    }
}

package jvm;


import javafx.geometry.VPos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 119页
 *
 * jConsole
 * 线程死循环
 * 线程锁等待
 *
 * */
public class UnderstandJVM119 {

    public static void createBusyThread() {
        new Thread(() -> {
            while (true)
                ;
        }, "testBusyThread").start();
    }

    public static void createLockThread(final Object lock){
        new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread").start();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        createLockThread(new Object());
    }

}

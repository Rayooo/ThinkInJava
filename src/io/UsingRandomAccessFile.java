package io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Ray on 2017/4/9.
 */
public class UsingRandomAccessFile {
    private static String file = "./src/io/rtest.dat";

    private static void display() throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < 7; i++) {
            System.out.println("Value " + i + ": " + rf.readDouble());
        }
        System.out.println(rf.readUTF());
        rf.close();
    }

    @Test
    public void main() throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file,"rw");
        for (int i = 0; i < 7; i++) {
            rf.writeDouble(i * 1.414);
        }
        rf.writeUTF("The end of file");
        display();

        rf.seek(5*8);
        rf.writeDouble(47.0001);
        rf.close();
        display();
    }

}

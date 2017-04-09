package io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Ray on 2017/4/9.
 */

/*

缓冲输入文件，如果想要打开一个文件用于字符输入，可以使用String或File对象作为文件名的FileInputReader，为了提高速度，
我们希望对其进行缓冲，那么我们将所产生的引用传给一个BufferedReader构造器，由于BufferedReader也提供readLine()方法，
所以这是我们的最终对象和进行读取的接口，见io/BufferedInputFile.java，readLine()会讲\n删去，所以要手动增加\n。

* */

public class BufferedInputFile {
    public static String read(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine())!= null){
            sb.append(s).append("\n");
        }
        in.close();
        return sb.toString();
    }
    @Test
    public void main(){
        try {
            System.out.println(read("./src/io/BufferedInputFile.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

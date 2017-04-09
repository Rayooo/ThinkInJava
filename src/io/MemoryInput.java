package io;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Ray on 2017/4/9.
 */
public class MemoryInput {
    @Test
    public void main() throws IOException {
//      BufferedInputFile将文件以String形式返回
//      从内存读入，从BufferedInputFile.read()读入的String结果被用来创建一个StringReader，
//      然后调用read()每次读一个字符，并把它发送给控制台，read()是以int形式返回，故要转成char
        StringReader in = new StringReader(BufferedInputFile.read("./src/io/MemoryInput.java"));
        int c;
        while ((c = in.read()) != -1){
            System.out.print((char)c);
        }

    }
}

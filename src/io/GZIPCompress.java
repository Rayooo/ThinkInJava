package io;

import org.junit.Test;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Ray on 2017/4/14 19:40
 */
public class GZIPCompress {
    @Test
    public void main() throws IOException {
        String[] args = {"aaa"};

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        BufferedOutputStream out = new BufferedOutputStream(
                new GZIPOutputStream(
                        new FileOutputStream("test.gz")));

        System.out.println("Writing File");
        int c;
        while ((c = in.read()) != -1){
            out.write(c);
        }

        in.close();
        out.close();

        System.out.println("Reading File");

        BufferedReader in2 = new BufferedReader(
                new InputStreamReader(
                        new GZIPInputStream(
                                new FileInputStream("test.gz"))));
        String s;
        while ((s = in2.readLine()) != null){
            System.out.println(s);
        }

    }
}

package io;

import org.junit.Test;

import java.io.*;

/**
 * Created by Ray on 2017/4/9.
 */
public class Redirecting {
    @Test
    public void main() throws IOException {
        PrintStream console = System.out;

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("./src/io/Redirecting.java"));

        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("./src/io/test.out")));

        System.setIn(in);
        System.setOut(out);
        System.setErr(out);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = br.readLine()) != null){
            System.out.println(s);
        }
        out.close();        //注意一定要关闭

        System.setOut(console); //恢复System.out

    }
}

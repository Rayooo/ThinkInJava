package io;

import org.junit.Test;

import java.io.*;

/**
 * Created by Ray on 2017/4/9.
 */
public class BasicFileOutput {

    private String file = "./src/io/BasicFileOutput.out";
    @Test
    public void main() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("./src/io/BasicFileOutput.java")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null){
            out.println(lineCount++ + ": " + s);
        }
        out.close();
        System.out.println(BufferedInputFile.read(file));
    }

}

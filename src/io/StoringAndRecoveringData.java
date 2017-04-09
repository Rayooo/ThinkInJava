package io;

import org.junit.Test;

import java.io.*;

/**
 * Created by Ray on 2017/4/9.
 */
public class StoringAndRecoveringData {
    @Test
    public void main() throws IOException {
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("./src/io/Data.txt")));
        out.writeDouble(3.14159);
        out.writeUTF("That's PI");
        out.writeDouble(1.41413);
        out.writeUTF("That's Square root of 2");
        out.close();

        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream("./src/io/Data.txt")));

        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());

    }
}

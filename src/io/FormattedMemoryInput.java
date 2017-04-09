package io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Ray on 2017/4/9.
 */
public class FormattedMemoryInput {
    @Test
    public void main() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                        BufferedInputFile.read("./src/io/FormattedMemoryInput.java").getBytes()
                )
        );

        while (in.available() != 0){
            System.out.print((char)in.readByte());
        }
    }
}

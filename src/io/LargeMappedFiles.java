package io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Ray on 2017/4/9.
 */

/*

内存映射文件，内存映射文件允许我们创建和修改那些因为太大而不能放入内存的文件，有了它，我们就可以假定整个文件都放在内存中，
而且可以完全把它当作非常大的数组来访问，见io/LargeMappedFiles.java，MappedByteBuffer由ByteBuffer继承而来，
因此它具有ByteBuffer的所有方法。上面那个程序创建了128M的文件，我们访问了中间一部分，程序将一部分文件放入了内存，
其他部分被交换了出去，用这种方式，很大的文件可以很容易地被修改，能够提高性能

* */

public class LargeMappedFiles {
    static int length = 0x8FFFFFF; //128MB

    @Test
    public void main() throws IOException {
        MappedByteBuffer out = new RandomAccessFile("./src/io/test.dat", "rw").getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, length);

        for (int i = 0; i < length; i++) {
            out.put((byte)'x');
        }
        System.out.println("Finished writing");
        for(int i = length/2; i < length/2 + 6; ++i){
            System.out.println(out.get(i));
        }
    }


}

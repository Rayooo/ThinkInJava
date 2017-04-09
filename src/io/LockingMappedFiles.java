package io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by Ray on 2017/4/9.
 */
public class LockingMappedFiles {

    static final int LENGTH = 0x8FFF;    //128MB
    static FileChannel fileChannel;

    @Test
    public void main() throws IOException {
        fileChannel = new RandomAccessFile("./src/io/test.dat","rw").getChannel();
        MappedByteBuffer out = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            out.put((byte)'x');
        }

        new LockAndModify(out, 0, LENGTH / 3);
        new LockAndModify(out, LENGTH/2, LENGTH/2 + LENGTH/4);

    }



    class LockAndModify extends Thread{
        private ByteBuffer buffer;
        private int start, end;

        LockAndModify(ByteBuffer mbb, int start, int end){
            this.start = start;
            this.end = end;
            mbb.limit(end);
            mbb.position(start);
            buffer = mbb.slice();
            start();
        }

        @Override
        public void run() {
            try {
                FileLock fl = fileChannel.lock(start, end, false);
                System.out.println("Locked : " + start + " to " + end);

                while (buffer.position() < buffer.limit() - 1){
                    buffer.put((byte)(buffer.get() + 1));
                }
                fl.release();
                System.out.println("Released: " + start + " to " + end);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


package io;

import org.junit.Test;

import java.io.File;

/**
 * Created by Ray on 2017/4/9.
 */
public class MakeDirectories {

    private static void usage(){
        System.err.println("输入错误");
        System.exit(1);
    }

    private static void fileData(File f){
        System.out.println("Absolute path: " + f.getAbsolutePath());
        System.out.println("Can read: " + f.canRead());
        System.out.println("Can write: " + f.canWrite());
        System.out.println("Get name: " + f.getName());
        System.out.println("Get Parent: " + f.getParent());
        System.out.println("Get Path: " + f.getPath());
        System.out.println("Length: " + f.length());
        System.out.println("Last modified: " + f.lastModified());
        System.out.println(f.isFile()? "It's a file":"It's a directory");
    }

    @Test
    public void main(){

//        String[] args = {"-d", "ioTest/a/b/aa"};
        String[] args = {"-r", "ioTest/a/b/aaa", "ioTest/a/ccc"};

        //重命名或移动 -r path1 path2
        if(args[0].equals("-r")){
            if(args.length != 3){
                usage();
            }
            File old = new File(args[1]);
            File rname = new File(args[2]);
            old.renameTo(rname);
            fileData(old);
            fileData(rname);
            return;
        }
        //删除 -d path1  不能删目录
        int count = 0;
        boolean del = false;
        if(args[0].equals("-d")){
            count++;
            del = true;
        }
        count--;
        while (++count < args.length){
            File f = new File(args[count]);
            if(f.exists()){
                System.out.println(f + " exists");
                if(del){
                    System.out.println("deleting..." + f);
                    System.out.println(f.delete());;
                }
            }
            //创建目录 path1
            else{
                if(!del){
                    f.mkdirs();
                    System.out.println("created " + f);
                }
            }
            fileData(f);
        }
    }


}

package io;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Ray on 2017/4/8.
 */
public class DirList {
    @Test
    public void main(){
        String[] args = {};

        File path = new File(".");      //.代表当前目录
        String[] list;
        if(args.length == 0){
            list = path.list();
        }
        else{
            list = path.list((dir, name) -> Pattern.compile(args[0]).matcher(name).matches());
        }

        if (list != null) {
            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
            for (String dirItem : list) {
                System.out.println(dirItem);
            }
        }
    }


    @Test
    public void pathTest() throws IOException {
        Path path = Paths.get(".");
        System.out.println(path.getFileName());     //DirList.java
        System.out.println(path.getName(0));        //Users
        System.out.println(path.getRoot());         //  /
        System.out.println(path.endsWith("DirList.java"));  //true
        System.out.println(path.getFileSystem());   //sun.nio.fs.MacOSXFileSystem
        System.out.println(path.getNameCount());        //8
//        System.out.println(path.subpath(2,5));          //  Documents/GitHub/ThinkInJava
        System.out.println(path.isAbsolute());
        System.out.println(path.normalize());           //去除冗余  如 /home/../etc/../../a 会去掉../
        System.out.println(path.toRealPath(LinkOption.NOFOLLOW_LINKS));    //   /Users/Ray/Documents/GitHub/ThinkInJava
    }

    @Test
    public void fileTest() {
        File file = new File(".");
        System.out.println(file.getAbsoluteFile());
    }

}

/*

.DS_Store
.git
.idea
docs
lib
out
README.md
src
ThinkInJava.iml

* */
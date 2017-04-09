package io;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
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
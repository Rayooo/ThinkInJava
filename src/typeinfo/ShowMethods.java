package typeinfo;


import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Create by Ray on 2017/4/23 12:25
 */
public class ShowMethods {

    private static Pattern p = Pattern.compile("\\w+\\.");

    @Test
    public void main(){
        String[] args = {"typeinfo.ShowMethods"};
        int lines = 0;

        try {
            Class<?> c = Class.forName(args[0]);
            Method[] methods = c.getMethods();
            Constructor[] constructors = c.getConstructors();
            if(args.length == 1){
                for(Method method : methods){
                    System.out.println(p.matcher(method.toString()).replaceAll(""));
                }

                for(Constructor constructor : constructors){
                    System.out.println(p.matcher(constructor.toString()).replaceAll(""));
                }
                lines = methods.length + constructors.length;
            }
            else{
                for(Method method : methods){
                    if(method.toString().contains(args[1])){
                        System.out.println(p.matcher(method.toString()).replaceAll(""));
                        lines++;
                    }
                }
                for(Constructor constructor : constructors){
                    if(constructor.toString().contains(args[1])){
                        System.out.println(p.matcher(constructor.toString()).replaceAll(""));
                        lines++;
                    }
                }
            }
            System.out.println(lines);



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



}

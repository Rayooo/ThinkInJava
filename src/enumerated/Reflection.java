package enumerated;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ray on 2017/4/4.
 */

enum Explore { HERE, THERE }

public class Reflection {

    public static Set<String> analyze(Class<?> enumClass){
        System.out.println("------Analyzing " + enumClass + " -------");
        System.out.println("Interfaces:");
        for (Type t : enumClass.getGenericInterfaces()){
            System.out.println(t);
        }

        System.out.println("Base : " + enumClass.getSuperclass());

        Set<String> methods = new TreeSet<>();
        System.out.println("Methods: ");
        for(Method m : enumClass.getMethods()){
            methods.add(m.getName());
        }
        System.out.println(methods);
        return methods;
    }

    public static void main(String[] args){
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);

        System.out.println("Explore.containsAll(Enum)? " + exploreMethods.containsAll(enumMethods));
        System.out.println("Explore.removeAll(Enum): " + exploreMethods.removeAll(enumMethods));

        System.out.println(exploreMethods);

    }

}

/*

------Analyzing class enumerated.Explore -------
Interfaces:
Base : class java.lang.Enum
Methods:
[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, values, wait]
------Analyzing class java.lang.Enum -------
Interfaces:
java.lang.Comparable<E>
interface java.io.Serializable
Base : class java.lang.Object
Methods:
[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, wait]
Explore.containsAll(Enum)? true
Explore.removeAll(Enum): true
[values]

* */

package java8;

import java.util.HashMap;
import java.util.Map;

/**
 * Create By Ray 2017/4/26 14:00
 */
public class MapApiTest {

    public static void main(String[] args){
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i,"val" + i);      //如果map中有这个key了，就不会覆盖其value
        }
        System.out.println(map);

//        map.forEach((integer, s) -> System.out.println(integer + " " + s));

        map.computeIfPresent(3, (integer, s) -> s + integer);
        System.out.println(map.get(3));                 //val33

        map.computeIfPresent(9, (integer, s) -> null);
        System.out.println(map.get(9));                 //null
        System.out.println(map.containsKey(9));         //false

        map.computeIfAbsent(23, integer -> "val" + integer);
        System.out.println(map.containsKey(23));        //true

        map.computeIfAbsent(3,integer -> "bam" + integer);
        System.out.println(map.get(3));                 //并不能放进去，val33

        map.remove(3, "val3");                  //要完全匹配才能删除
        System.out.println(map.get(3));         //val33

        map.remove(3,"val33");
        System.out.println(map.get(3));         //null

        System.out.println(map.getOrDefault(42,"not Found")); //没找到时默认值not Found

        map.merge(9,"val9", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9));     //val9      因为之前没有值，被删了，所以为初始化

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9));     //val9concat


    }

}

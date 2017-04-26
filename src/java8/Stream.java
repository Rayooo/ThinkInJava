package java8;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Create by Ray 2017/4/26 13:33
 */
public class Stream {

    public static void main(String[] args){
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");


        //Filter
        stringCollection
                .stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::print);      //aaa2 aaa1

        System.out.println();

        //Sorted
        stringCollection
                .stream()
                .sorted()                           //sort可以传递一个比较函数
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::print);        //aaa1 aaa2
        System.out.println();


        //sorted只在stream内排好序，在外部并未排序
        System.out.println(stringCollection);       //[ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1]


        //Map
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .forEach(s -> System.out.print(s + " "));   //DDD2 DDD1 CCC BBB3 BBB2 BBB1 AAA2 AAA1
        System.out.println();

        //Match
        boolean anyStartWithA = stringCollection.stream().anyMatch(s -> s.startsWith("a"));
        System.out.println(anyStartWithA);      //true

        boolean allStartWithA = stringCollection.stream().allMatch(s -> s.startsWith("a"));
        System.out.println(allStartWithA);      //false

        boolean noneStartsWithZ = stringCollection.stream().noneMatch(s -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);    //true


        //Count
        long startsWithB = stringCollection.stream().filter(s -> s.startsWith("b")).count();
        System.out.println(startsWithB);        //3


        //Reduce
        //This terminal operation performs a reduction on the elements of the stream with the given function.
        // The result is an Optional holding the reduced value.
        Optional<String> reduced = stringCollection.stream().sorted().reduce((s, s2) -> s + "#" + s2);
        reduced.ifPresent(System.out::println);     //aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2



    }

    @Test
    public void testStream() {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();

        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
        //sequential sort took: 955 ms


    }

    @Test
    public void testParallelStream() {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
        //parallel sort took: 409 ms
    }

}

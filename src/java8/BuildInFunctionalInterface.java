package java8;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Create By Ray 2017/4/26 11:29
 */
public class BuildInFunctionalInterface {

    public static void main(String[] args){
        Predicate<String> predicate = s -> s.length()>0;        //返回一个bool值
        System.out.println(predicate.test("123"));
        System.out.println(predicate.negate().test("123"));


        Function<String, Integer> toInteger = Integer::valueOf;
        System.out.println(toInteger.apply("1234"));


        Supplier<Person> personSupplier = Person::new;
        Person person = personSupplier.get();

        Consumer<Person> greeter = p -> System.out.println("Hello " + p.toString());
        greeter.accept(new Person("ray","aaa"));


        //还有个Comparators，比较常用，不写了


        //Optional可以有效地防止返回NullPointer
        Optional<String> optional = Optional.of("bam");

        System.out.println(optional.isPresent());           // true
        System.out.println(optional.get());                 // "bam"
        System.out.println(optional.orElse("fallback"));    // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"

    }

}

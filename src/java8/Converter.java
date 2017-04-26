package java8;

import org.junit.Test;

/**
 * Create by Ray 2017/4/26 10:41
 */

@FunctionalInterface
interface ConverterInterface<F, T> {
    T convert(F from);
}

public class Converter {

    @Test
    public void main(){
        ConverterInterface<String, Integer> converter = from -> Integer.valueOf(from);
        ConverterInterface<String, Integer> converter2 = Integer::valueOf;  //也可以这么写

        Integer converted = converter.convert("123");
        System.out.println(converted);
    }

}

package java8;

import org.junit.Test;

/**
 * Create by Ray 2017/4/26 10:51
 */

class Something {
    String startWith(String s){
        return String.valueOf(s.charAt(0));
    }
}


public class QuoteExpression {

    @Test
    public void main(){
        Something something = new Something();
        ConverterInterface<String, String> converter = something::startWith;
        String converted = converter.convert("Java");       //J
        System.out.println(converted);

    }

}

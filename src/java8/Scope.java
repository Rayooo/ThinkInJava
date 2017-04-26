package java8;

/**
 * Create by Ray 2017/4/26 11:14
 */
public class Scope {

    public static void main(String[] args){
        final int num = 1;
        ConverterInterface<Integer, String> stringConverter = from -> String.valueOf(from + num);

        System.out.println(stringConverter.convert(2)); //3


        int num2 = 1;
        ConverterInterface<Integer, String> stringConverter2 = from -> String.valueOf(from + num2);

        System.out.println(stringConverter2.convert(2));    //3

        //num2 = 3;           //编译错误，在lambda中使用过后，不能重新赋值了

    }

}

class Lambda4 {
    static int outerStaticNum;
    int outerNum;

    void testScopes() {
        ConverterInterface<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        ConverterInterface<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}
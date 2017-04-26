## Java 8 特性

### interface的默认方法

Java8可以为接口增加非抽象的方法，使用 default 关键字

```java
interface FormulaInterface {

    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}

public class Formula{
    @Test
    public void main(){
        FormulaInterface formula = new FormulaInterface() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(16));
    }
}
```

### Lambda表达式

```java
List<String> names = Arrays.asList("peter", "anna", "milk", "xenia");

names.sort(new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
        return o2.compareTo(o1);
    }
});

names.sort((String a, String b) -> {
    return a.compareTo(b);
});

names.sort((o1, o2) -> o2.compareTo(o1));

names.sort(Comparator.reverseOrder());
```

使用lambad实现Comparator接口，如果只有一句语句，可以省略 { } 和 return 语句

### Functional Interfaces

lambda表达式如何引入java原始的类型呢，每个lambda批评一个interface，每个functional interface 必须包含一个抽象方法(abstract method)，注意添加@FunctionalInterface这个注解

```java
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
```

### 方法和构造器的引用

```java
@FunctionalInterface
interface ConverterInterface<F, T> {
    T convert(F from);
}

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
```

Java8允许使用 :: 引用一个方法，如 ConverterInterface 的实现， converter引用了Something中的一个方法

```java
public class Person {

    private String firstName;

    private String lastName;

    public Person() {}

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public static void main(String[] args){
        PersonFactory<Person> personPersonFactory = Person::new;
        Person person = personPersonFactory.create("Ray","chen");
        System.out.println(person);
    }
}

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}

```

关键字 :: 也可以让我们引用构造器

### Lambda 作用域

从lambda获取外部变量与匿名类非常相似，你可以获得到final变量和实例域和静态变量

```java
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
```

### 内置Functional Interface

```java
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
```
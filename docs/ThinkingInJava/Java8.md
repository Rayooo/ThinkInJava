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

### Streams

java.util.Stream 表示了对元素的一系列操作，在Collections中，可以调用Collection.stream()或Collection.parallelStream()

```java
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

```

### Parallel Streams

stream可以是单线程的或是并发的，并发可以使用多个线程来操作，使用并发来操作速度更加快

```java
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
```

### Map

```java
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

        
```

### Date API

```java
Clock clock = Clock.systemDefaultZone();
long millis = clock.millis();

Instant instant = clock.instant();
Date legacyDate = Date.from(instant);

System.out.println(millis);         //1493187857378
System.out.println(legacyDate);     //Wed Apr 26 14:24:17 CST 2017


System.out.println(ZoneId.getAvailableZoneIds());
ZoneId zone1 = ZoneId.of("Europe/Berlin");
ZoneId zone2 = ZoneId.of("Brazil/East");
System.out.println(zone1.getRules());
System.out.println(zone2.getRules());



LocalTime now1 = LocalTime.now(zone1);
LocalTime now2 = LocalTime.now(zone2);

System.out.println(now1.isBefore(now2));  // 测试哪个时间在前false

long hoursBetween = ChronoUnit.HOURS.between(now1, now2);   //计算时间差
long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

System.out.println(hoursBetween);       // -3
System.out.println(minutesBetween);     // -239



//本地化时间
LocalTime late = LocalTime.of(23, 59, 59);
System.out.println(late);       // 23:59:59

DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);

LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
System.out.println(leetTime);   // 13:37




LocalDate today = LocalDate.now();      //修改时间，增加时间
LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
LocalDate yesterday = tomorrow.minusDays(2);
System.out.println(tomorrow);       //2017-04-27
System.out.println(yesterday);      //2017-04-25

LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
System.out.println(dayOfWeek);    // FRIDAY



DateTimeFormatter formatter =
        DateTimeFormatter
                .ofPattern("MM dd, yyyy - HH:mm");

LocalDateTime parsed = LocalDateTime.parse("12 03, 2014 - 07:13", formatter);
String string = formatter.format(parsed);
System.out.println(string);     //12 03, 2014 - 07:13
```

### 注解

注解可以重复多个

```
@interface Hints {
    Hint[] value();
}

@Repeatable(Hints.class)
@interface Hint {
    String value();
}

@Hints({@Hint("hint1"), @Hint("hint2")})
class Person {}

@Hint("hint1")
@Hint("hint2")
class Person {}
```
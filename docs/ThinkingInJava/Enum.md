## 枚举

### 基本特性

1.  调用enum的values()方法，可遍历enum实例，values()方法返回enum实例的数组，而且该数组严格保持其在enum中声明的顺序，见EnumClass.java，ordinal()方法返回一个int值，这是每个enum实例在声明时的次序，从0开始。可以使用==来比较enum实例。

### 向enum中添加新的方法

1.  除了不能继承来自另一个enum之外，可以将enum看作一个常规的类，我们可以向enum添加新的方法，见OzWitch.java ，
2.  可以覆盖enum的方法，如toString()

### Values()的神秘之处

1.  Enum并没有Values()方法，values()是由编译器添加的static方法，通过反射来分析Enum类，见Reflection.java

### 随机选取

1.  利用泛型，编写一个类从enum中进行随机选取，见EnumRandom.java
2.  <T extends Enum<T> > 表示T是一个enum实例，而将Class<T> 作为参数的话，我们就可以利用Class对象得道enum实例的数组了，重载后的random()方法只需使用T[]作为参数，因为它并不会跳用Enum上的任何操作，它只需从数组中随机选择一个元素即可，它能消除很多重复的代码。 

### EnumSet & EnumMap

EnumSet 中的元素必须来自于一个enum，EnumMap中的key必须来自于一个enum，
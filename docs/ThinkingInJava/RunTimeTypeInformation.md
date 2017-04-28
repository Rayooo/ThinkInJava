## 类型信息

RTTI(Run-time type information)运行时类型信息，使得你可以在程序运行时发现和使用类型信息

### Class对象

1.要理解RTTI在Java中的工作原理，首先必须知道类型信息在运行时是如何表示的，这项工作是由称为Class对象的特殊对象完成的，它包含了与类有关的信息。Java使用Class对象来执行其RTTI，如转型这样的操作。Class还拥有大量使用RTTI的其他方式。

2.所有的类都是在对其第一次使用时，动态加载到JVM中的，当程序创建第一个对类的静态成员引用时，就会加载这个类。Java程序在它开始运行之前并非被完全加载，其各个部分是在必需时才加载的。类加载器首先检查这个类的Class对象是否已经加载，如尚未加载，默认的类加载器就会根据类名查找.class文件。在被加载时，会被接受验证，以保证没有被损坏。一旦某个类的Class对象被载入内存，它就被用来创建这个类的所有对象。见typeinfo/SweetShop.java ，static子句在类第一次被加载时执行。

3.无论何时，只要想在运行时使用类型信息，就必须首先获得对恰当的Class对象的引用。Class.forName()就是实现此功能的便捷途径，因为你不需要为了获得Class引用而持有对象。但是如果已经拥有了一个感兴趣的类型对象，那么就可以调用getClass()方法来获得Class引用。Class包含很多方法，见typeinfo/toy/ToyTest.java

4.类字面常量，如Fancy.class，这样做不仅更简单，而且更安全，他在编译时就会受到检查（因此不需要放到try块中），并且根除了对forName()的调用。当使用 .class 来创建对Class对象的引用时，不会自动地初始化该Class对象。为了使用类而做的准备工作实际包含三个步骤：

-   加载，由类加载器执行，该步骤将查找字节码，并从这些字节码中创建一个Class对象。
-   链接，在链接阶段将严重类中的字节码，为静态域分配存储空间，并且如果需要的话，将解析这个类创建对其他类的所有引用
-   初始化，如果该类有超类，则对其初始化，执行静态初始化器和静态初始化块。

5.初始化有效地实现了尽可能的惰性，见typeinfo/ClassInitialization.java，仅使用 .class 语法来获得对类的引用不会引发初始化，但是，Class.forName()立即就进行了初始化。如果一个static final值是编译期常量，那么这值不需要对类进行初始化久可以读取，但是如果只是将一个域设置为static和final的，还不是以确保这种行为，那么对该属性的访问将强制进行类的初始化，因为它不是一个编译期的常量。如果一个static域不是final的，那么在对它访问时，总是要求在它被读取之前，要先进行链接（为这个域分配存储空间）和初始化（初始化该存储空间）

6.泛化的Class引用，Class<?> 优于平凡的Class，即便它们是等价的，平凡的Class不会产生编译器警告信息，通配符与extends结合，创建一个范围，如Class<? extends Number>，或Class<? super FancyToy>，见typeinfo/FilledList.java

### 类转型前先做检查

1.编译器运行自由地做向上转型的赋值操作，而不需要任何显式的转型操作。如编译器知道Circle肯定是一个Shape。但是编译器无法知道对于Shape到底是什么Shape，它可能就是Shape，或是Shape的子类型，如Circle，Square，Triangle或其他。因此，如果不使用显示的类型转换，编译器就不允许你执行向下转型赋值。使用instanceof检查对象是不是某个特定类型的实例。

2.Object.getClass() 与 Class.class 与 instanceof ，instanceof保持了类型的概念，它指的是“你是这个类吗，或者你是这个类的派生类吗？”而如果用 getClass() 或是 .class 就不考虑继承，它是这个确切的类型，或者不是。

### 反射

1.反射机制提供了足够的支持，使得能够创建一个在编译器完全未知的对象，并调用此对象的方法。

2.Class的getMethods() 和 getConstructors() 方法分别返回Method对象的数组和Constructors对象的数组。这两个类都提供了深层的方法，用以解析其对象所代表的方法，并获取其名字，输入参数及返回值。见typeinfo/ShowMethods

```java
for (Method method : userBeanClass.getDeclaredMethods()) {
    if(method.isAnnotationPresent(Invoke.class)){   //判断是否被@Invoke修饰
        if(Modifier.isStatic(method.getModifiers())){   //如果是static方法
            method.invoke(method,"Ray");
        }
        else{
            Class[] params = {String.class, int.class};
            Constructor constructor = userBeanClass.getDeclaredConstructor(params);// 获取参数格式为 String,long 的构造函数
            Object userBean = constructor.newInstance("Ray",123); // 利用构造函数进行实例化，得到 Object
            if(Modifier.isPrivate(method.getModifiers())){
                method.setAccessible(true);         //如果是private方法，需要获取其调用权限
            }
            method.invoke(userBean);
        }

    }
}
```

3.调用方法，见reflection/ReflectionTest

### 动态代理

1.Java的动态代理可以动态地创建代理并动态地处理对所代理的方法调用。在动态代理上所做的所有调用都会被重定向到单一的调用处理器上。

2.通过调用静态方法Proxy.newProxyInstance()可以创建动态代理，这个方法需要得到一个类加载器，一个你希望该代理实现的接口列表，以及InvocationHandler接口的一个实现。动态代理可以将所有调用重定向到调用处理器，因此通常会向调用处理器的构造器传递给一个实际对象的引用，从而使得调用处理器在执行其中介任务时，可以将请求转发。见typeinfo/SimpleDynamicProxy.java
## 泛型

### 通配符

```java
//具有任何从Fruit继承的类型的列表
List<? extends Fruit> fruitList = new ArrayList<>();
//fruitList.add(new Fruit());     //都不能添加
//fruitList.add(new Apple());
//fruitList.add(new Object());

fruitList.add(null);
Fruit fruit = fruitList.get(0);     //能返回Fruit类型的
```

1.fruitList类型是List<? extends Fruit>，可以将其读作具有任何从Fruit继承的类型的列表。但是这实际上并不意味着这个List将持有任何类型的Fruit。通配符引用的是明确的类型，因此它意味着 某种fruitList引用没有指定的具体类型。因此这个被赋值的List必须持有诸如Fruit或Apple这样的某种指定类型，但是为了向上转型为fruitList，这个类型是什么并没有人关心。如果调用返回一个Fruit的方法，则是安全的，因为你知道在这个List中的任何对象至少具有Fruit类型，因此编译器允许这么做。
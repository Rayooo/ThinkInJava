## 容器

基本类型List,Set,Queue,Map

### 基本概念

1.  Collection 一个独立元素的序列，这些元素都服从一条或多条规则，List必须按照插入的顺序保存元素，Set不能有重复元素，Queue按照排队规则来确定对象产生的顺序
2.  Map 一组成对的 键值对 对象，允许使用键来查找值。ArrayList允许你使用数字来查找值，因此在某种意义上讲，它将数字与对象关联在一起。


### Collection

1.  功能方法
    -   boolean add(T)
    -   boolean addAll(Collection<? extends T>)
    -   void clear()
    -   boolean contains(T)
    -   Boolean containsAll(Collection<?>)
    -   boolean isEmpty()
    -   Iterator<T> iterator()
    -   Boolean remove(Object)
    -   boolean removeAll(Collection<?>)
    -   Boolean retainAll(Collection<?>)
    -   int size()
    -   Object[] toArray()
    -   <T> T[] toArray(T[] a)
2.  不包括随机访问所选单元get()方法，因为Collection包括Set，而Set是自己维护内部顺序的，如果想检查Collection中的元素，必须使用迭代器

### List

### Set

1.  比较
    -   Set(interface)   存入Set的每个元素都必须是唯一的，因为Set不保存重复元素，加入Set的元素必须定义equals()方法以确保对象的唯一性，Set和Collection有完全一样的接口，Set接口不保证维护元素的次序
    -   HashSet  默认选择，为快速查找而设计的Set，存入HashSet的元素必须定义HashCode()
    -   TreeSet   保持次序的Set，底层为树结构（红黑树结构），使用它可以从Set中提取有序的序列，元素必须实现Comparable接口
    -   LinkedHashSet   具有HashSet的查询速度，且内部使用链表维护元素的顺序（插入的次序）。于是在使用迭代器遍历Set时，结果会按元素插入的次序显示，元素也必须定义hashCode()方法
2.  必须为散列储存和树型储存都创建一个equals()方法，但是hashCode()只有在这个类将会被置于HashSet或者LinkedHashSet中才是必须的，但是对于良好的编程风格而言，应该在覆盖equals()方法时，同时覆盖hashCode()方法


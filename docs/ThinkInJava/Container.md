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

1.  List（接口）承诺可以将元素维护在特定的序列，List接口在Collection的基础上添加了大量的方法，使得可以在List的中间插入和移除元素
2.  ArrayList，它的优势在于随机访问元素，但是在List的中间插入和移除元素时较慢。<u>**以数组实现**</u>。节约空间，但数组有容量限制。超出限制时会增加50%容量，用System.arraycopy()复制到新的数组，因此最好能给出数组大小的预估值。默认第一次插入元素时创建大小为10的数组。按数组下标访问元素—get(i)/set(i,e) 的性能很高，这是数组的基本优势。直接在数组末尾加入元素—add(e)的性能也高，但如果按下标插入、删除元素—add(i,e), remove(i), remove(e)，则要用System.arraycopy()来移动部分受影响的元素，性能就变差了，这是基本劣势。ArrayList是非线程安全的，在多线程环境下建议使用CopyOnWriteArrayList
3.  LinkedList，它通过代价较低的在List中间进行的插入和删除操作，提供了优化的顺序访问，LinkedList在随机访问方面相对比较慢，但它的特性集较ArrayList更大。LinkedList使用**<u>双向链表</u>**实现，线程不安全
4.  Vector 线程安全但是效率低，但已过时，不建议使用

### Set

1.  比较
    -   Set(interface)   存入Set的每个元素都必须是唯一的，因为Set不保存重复元素，加入Set的元素必须定义equals()方法以确保对象的唯一性，Set和Collection有完全一样的接口，Set接口不保证维护元素的次序
    -   HashSet  默认选择，为快速查找而设计的Set，存入HashSet的元素必须定义HashCode()
    -   TreeSet   保持次序的Set，底层为树结构（红黑树结构），使用它可以从Set中提取有序的序列，元素必须实现Comparable接口
    -   LinkedHashSet   具有HashSet的查询速度，且内部使用链表维护元素的顺序（插入的次序）。于是在使用迭代器遍历Set时，结果会按元素插入的次序显示，元素也必须定义hashCode()方法

2.  必须为散列储存和树型储存都创建一个equals()方法，但是hashCode()只有在这个类将会被置于HashSet或者LinkedHashSet中才是必须的，但是对于良好的编程风格而言，应该在覆盖equals()方法时，同时覆盖hashCode()方法

3.  SortedSet（接口），SortedSet中的元素可以保证处于排序状态，这使得它可以通过在SortedSet接口中的下列方法提供附加的功能
    -   Comparator comparator() 返回当前Set的子集，范围从fromElement（包含）到toElement（不包含）
    -   Object first()  返回容器中的第一个元素
    -   Object last() 返回容器中的最末一个元素
    -   SortedSet subSet(fromElement, toElement) 生成此Set的子集，范围从fromElement（包含） 到 toElement（不包含）
    -   SortedSet headSet(toElement) 生成此Set的子集，由小于toElement的元素组成
    -   SortedSet tailSet(fromElement) 生成此Set的子集，由大于或等于fromElement的元素组成
    -   例子SortedSetDemo.java


### Queue

1.  LinkedList提供了方法以支持队列的行为，并且它实现了Queue接口，因此LinkedList可以用作Queue的一种实现
2.  PriorityQueue（类），优先级队列，通过自己的Comparator来修改优先级队列的排队顺序
3.  Deque（没有实现），双向队列，在LinkedList中包含双向队列的方法，但是在Java标准类库中没有任何显示的用于双向队列的接口。因此LinkedList无法去实现这样的接口，也无法向上转型成Queue这样去向上转型到Deque，但是可以组合创建一个Deque类，并且直接从LinkedList中暴露相关的方法。

### Stack

1.  LinkedList具有能够直接实现栈的所有功能的方法，因此可以直接将LinkedList作为栈使用



### Map

HashMap

HashTable

TreeMap


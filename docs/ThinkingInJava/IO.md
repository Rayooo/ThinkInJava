## Java I/O 系统

### File类

1.目录列表器，见io/DirList.java，list()方法获得File类中的文件信息，也可以回调实现FilenameFilter接口中的accept()方法，决定哪些文件包含在列表中，这是一个策略模式的例子，可以用lambda表达式来实现这个接口

```java
        String[] args = {};

        File path = new File(".");      //.代表当前目录
        String[] list;
        if(args.length == 0){
            list = path.list();
        }
        else{
            list = path.list((dir, name) -> Pattern.compile(args[0]).matcher(name).matches());
        }
```

2.目录的检查与创建，可以使用File对象来创建新的目录或者尚不存在的整个目录路径，还可以查看文件的特性，如大小，修改日期，检查某个File对象是文件还是目录，并且可以删除文件，实现移动或重命名文件，见io/MakeDirectories.java。

### 输入和输出

1.InputStream，它的作用是用来表示那些从不同数据源产生输入的类，包括字节数组，String对象，文件，管道，一个由其他种类的流组成的序列，其他的数据源，如Internet连接。InputStream类型有

-   ByteArrayInputStream，允许将内存的缓冲区当作InputStream使用
-   StringBufferInputStream，将String转换成InputStream
-   FileInputStream，用于从文件中读取信息
-   PipedInputStream，产生用于写入相关PipedOutputStream的数据，实现管道化的概念
-   SequenceInputStream，将两个或多个InputStream对象转换成单一InputStream
-   FileInputStream，抽象类，作为装饰器的接口

2.OutputStream，决定了输出所要去往的目标：字节数组，文件，管道，OutputStream的类型有

-   ByteArrayOutputStream，在内存中创建缓冲区，所有送往“流”的数据都要放置在此缓冲区
-   FileOutputStream，将信息写入文件
-   PipedOutputStream，任何写入其中的信息都会自动作为相关PipedInputStream的输出，实现管道化
-   FilterOutputStream 抽象类，作为装饰器的接口

### I/O流的典型使用方式

1.缓冲输入文件，如果想要打开一个文件用于字符输入，可以使用String或File对象作为文件名的FileInputReader，为了提高速度，我们希望对其进行缓冲，那么我们将所产生的引用传给一个BufferedReader构造器，由于BufferedReader也提供readLine()方法，所以这是我们的最终对象和进行读取的接口，见io/BufferedInputFile.java，readLine()会讲\n删去，所以要手动增加\n。

2.从内存读入，从BufferedInputFile.read()读入的String结果被用来创建一个StringReader，然后调用read()每次读一个字符，并把它发送给控制台，read()是以int形式返回，故要转成char

3.格式化内存输入，要读取格式化数据，可以使用DataInputStream，它是一个面向字节的I/O类，不是面向字符的。因此必须使用InputStream类而不是Reader类。可以使用available()方法检查还有多少可供存取的字符，但是available()的工作方式会随着所读取的媒介类型的不同而有所不同，对于文件，这意味着整个文件，但是对于不同类型的流，可能就不是这样的，因此要谨慎使用

4.基本的文件输出，FileWriter对象可以向文件写数据，通常会用BufferedWriter将其包装起来用以缓冲输出，缓冲能显著增加IO性能。

5.储存和恢复数据，为了输出可供另一个 流 恢复的数据，我们需要用DataOutputStream写入数据，并用DataInputStream恢复数据，见io/StoringAndRecoveringData.java

6.读写随机访问文件，RandomAccessFIle，使用它时，必须知道文件的排版才能正确地操作它，见io/UsingRandomAccessFile.java，seek()可以产生查找位置，并且修改

7.管道流用于任务之间的通信

### 标准IO

1.按照标准IO模型，Java提供了System.in  System.out 和 System.err

2.标准IO重定向，见io/Redirecting.java

### 新IO

1.内存映射文件，内存映射文件允许我们创建和修改那些因为太大而不能放入内存的文件，有了它，我们就可以假定整个文件都放在内存中，而且可以完全把它当作非常大的数组来访问，见io/LargeMappedFiles.java，MappedByteBuffer由ByteBuffer继承而来，因此它具有ByteBuffer的所有方法。上面那个程序创建了128M的文件，我们访问了中间一部分，程序将一部分文件放入了内存，其他部分被交换了出去，用这种方式，很大的文件可以很容易地被修改，能够提高性能

2.文件加锁，它允许我们同步访问某个作为共享资源的文件，文件锁对其他的操作系统进程是可见的，Java的文件加锁直接映射到了本地操作系统的加锁工具，见io/FileLocking.java。调用tryLock()和lock()可以获得整个文件的FileLock，tryLock()是非阻塞式的，它设法获取锁，但是如果不能获得，它将直接从方法调用返回，lock()是阻塞式的，它要阻塞进程直到锁可以获得，或是调用lock()的线程中断，或者调用lock()的通道关闭。也可以指定参数使文件的一部分上锁

3.对映射文件的部分加锁，见io/LockingMappedFiles.java
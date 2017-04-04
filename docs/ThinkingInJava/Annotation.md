## 注解

Java内置三种标准注解 

@Override 表示当前的方法定义覆盖超类中的方法

@Deprecated 标记为不建议使用，过时的方法

@SuppressWarnings 关闭不当的编译器警告信息

四种元注解，元注解专职负责注解其他的注解

@Target  表示该注解可以用于什么地方，可能的ElementType参数包括：CONSTRUCTOR 构造器的声明 , FIELD 域声明（包括enum实例）, LOCAL_VARIABLE 局部变量声明 , METHOD 方法声明 , PACKAGE  包声明 ,  PARAMETER 参数声明 , TYPE 类，接口（包括注解类型）或enum声明。

@Retention  表示需要在什么级别保存该注解信息，可选的RetentionPolicy包括：SOURCE 注解将被编译器丢弃，@CLASS：注解在class文件中可用，但会被VM丢弃，@RUNTIME：VM将在运行期也保留注解，因此可以通过反射机制读取注解的信息

@Documented 将此注解包含在Javadoc中

@Inherited  允许子类继承父类中的注解

### 定义注解，编写注解处理器

见UseCase.java,  PasswordUtils.java, UseCaseTracker.java，Method.getAnnotation()方法返回指定类型的注解对象，如果被注解的方法上没有该类型的注解，则返回null值


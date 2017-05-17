## 服务框架

### 服务框架原型

假设有一个计算器的功能

```java
public class Calculator {
  	public int add(int a, int b){
      	return a + b;
  	}
  	public int minus(int a, int b){
      	return a - b;
  	}
  	public static void test1(){
  		Calculator calculator = new Calculator();
  		System.out.println(Calculator.add(1, 1));
	}
}


```

在开始之前，我们需要吧Calculator的接口抽象出来

```java
public interface Calculator {
  	int add(int a, int b);
  	int minus(int a, int b);
}
public class CalculatorImpl implements Calculator {
  	public int add(int a, int b){
      	return a + b;
  	}
  	public int minus(int a, int b){
      	return a - b;
  	}
}
```

在调用者这一方如果想调用这个方法，该怎么做呢？以下是伪代码

```java
//首先需要获取服务地址列表
List<String>  serviceAddress = getAvailableServiceAddresses("Caluctor.add");
//从服务地址列表中选择一个,确定要调用服务的目标机器
String address = chooseTarget(serviceAddress);

//然后使用socket建立连接
Socket s = new Socket(address);

//请求内容的序列化, Java本身的序列化就可以把对象转为二进制数据，使用起来非常简单
byte[] request = genRequest(a, b);

//发送请求
s.getOutputStream().write(request);

//接受结果，反序列化
byte[] aresponse = new byte[10240];
s.getInputStream().read(response);
```

服务端的伪代码实现

```java
public class EventHandler{
  	public static class Request {
      	public Socket socket;
      	public String serviceName;
      	public String serviceVersion;
      	public String methodName;
      	public Object[] args;
  	}
  	public static void eventHandler(){
      	while(true){
          	byte[] requestData = receiveRequest();
          	Request request = getRequest(requestData);
          	Object service = getServiceByNameAndVersion(request.serviceName, request.serviceVersion);
          	Object result = callService(service, request.methodName, request.args);
          	byte[] data = genResult(result);
          	request.socket.getOutputStream().write(data);
      	}
  	}
}
```
对于服务端，我们需要在启动后就进行监听，我们需要持续地接受请求并进行处理，而且对于收到的数据也需要一个反序列化的过程以得到对象本身，需要得到服务名称，服务版本号，需要调用的方法名称及参数，以及调用的连接 。在得到服务的具体实例后，接下来就是服务调用了，这一般是通过反射的方式来实现，得到服务的实例集体方法的执行结果后，需要返回给调用方 的结果序列化为二进制数据，通过网络写回给请求发送端。

### 远程通信遇到的问题

在实际过程中，并非一个调用者调用一个服务提供商，而是一个调用者集群，去调用服务提供商集群。我们采用透明代理与调用者，服务提供者直连的解决方案，例如引入一个服务注册查找中心，服务注册查找中心对于调用者来说，只是提供可用的服务提供者的列表。对于服务提供者来说，当服务提供者启动服务时，在服务注册查找中心中增加一个它的地址。服务注册查找中心可以进行优化，使得同一个网段内网之间相互调用，负载均衡等等。

### 服务升级

1.接口不变，代码本身进行完善，这样的升级比较简单，只是内部服务改变了

2.接口中增加方法，这样也比较简单，直接增加方法就好了

3.修改接口的某些方法调用的阐述列表。比较复杂，有以下几种方法解决：一、对使用原来的方法的代码都进行修改，然后和服务端一起发布，这个不太可行，因为我们需要同时发布多个系统，而且一些系统可能并不会从调整参数后的方法那里受益。二、通过版本号来解决，使用老方法的系统继续调用原来版本的服务，而需要使用新方法的系统则使用新版本的服务。三、在设计方法上考虑参数的扩展性，这是一个可行的方法，但是不太好，因为参数列表可扩展一般就意味着采用类似Map的方式来传递参数，这样不直观，而且对参数校验也比较复杂。

### 实战中的优化

有了服务框架，集中式系统就会很方便地转变为分布式系统，但是以下几个问题要注意

1.服务的拆分：要拆分的服务是需要为多方提供公共功能的，对于那些比较专用的实现，查出来它们是独立部署在远程机器上来提供服务的，这不仅没必要，而且会增加系统的复杂性

2.服务的粒度：很难量化回答的问题，只能根据业务的实际情况来划分服务

3.优雅和实用的平衡：服务化的架构看起来比较优雅，但是毕竟多一次调用就比之前多走了一次网络。例如如果从缓存取数据的话，就不要经过服务提供商取缓存，而是直接调用者直接取取缓存。

4.分布式环境中的请求合并。
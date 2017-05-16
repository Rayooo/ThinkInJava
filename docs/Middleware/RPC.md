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
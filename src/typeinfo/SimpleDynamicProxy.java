package typeinfo;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by Ray on2017/4/23 12:54
 */

class DynamicProxyHandler implements InvocationHandler{

    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("******* proxy: " + proxy.getClass() + ". method: " + method + ". args: " + args);
        if (args != null){
            for (Object arg : args) {
                System.out.println(" " + arg);
            }
        }
        //注意这个proxied，不是上面的Object proxy，否则会造成递归
        return method.invoke(proxied, args);
    }
}

interface MyInterface {
    void doSomething();
    void somethingElse(String arg);
}

class RealObject implements MyInterface{
    @Override
    public void doSomething() {
        System.out.println("doSomething");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("Something else " + arg);
    }
}

public class SimpleDynamicProxy {

    public static void consumer(MyInterface i){
        i.doSomething();
        i.somethingElse("boom");
    }

    @Test
    public void main(){
        //非代理
        RealObject real = new RealObject();
        consumer(real);

        //java的动态代理
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(MyInterface.class.getClassLoader(), new Class[]{MyInterface.class}, new DynamicProxyHandler(real));
        consumer(proxy);
    }

}

package reflection;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.*;

/**
 * Create by Ray 2017/4/27 16:29
 */

public class ReflectionTest{
    @Test
    public void main() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class userBeanClass = UserBean.class;
        //打印属性
        Field[] fields = userBeanClass.getDeclaredFields();   //getFields 只返回public的，需要使用getDeclaredFields
        for (Field field : fields) {
            StringBuilder fieldString = new StringBuilder();
            fieldString.append(Modifier.toString(field.getModifiers())).append(" ");        //private
            fieldString.append(field.getType().getSimpleName()).append(" ");                //String
            fieldString.append(field.getName());                                //userName
            fieldString.append(";");
            System.out.println(fieldString);
        }
        System.out.println();

        //打印方法
        Method[] methods = userBeanClass.getDeclaredMethods();
        for (Method method : methods) {
            StringBuilder methodString = new StringBuilder(Modifier.toString(method.getModifiers()) + " ");     //private static
            methodString.append(method.getReturnType()).append(" ");        //void
            methodString.append(method.getName()).append("(");              //staticMethod
            Class[] parameters = method.getParameterTypes();
            for (Class parameter : parameters) {
                methodString.append(parameter.getSimpleName()).append(" "); //String
            }
            methodString.append(")");
            System.out.println(methodString);
        }
        System.out.println();

        //打印构造函数
        Constructor[] constructors = userBeanClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            StringBuilder s = new StringBuilder(Modifier.toString(constructor.getModifiers()) + " ");
            s.append(constructor.getName()).append("(");
            Class[] parameters = constructor.getParameterTypes();
            for (Class parameter : parameters) {
                s.append(parameter.getSimpleName()).append(",");
            }
            s.append(")");
            System.out.println(s);
        }
        System.out.println();


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

    }
}


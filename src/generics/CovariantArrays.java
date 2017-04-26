package generics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on 2017/4/25 19:47
 */

class Fruit {}

class Apple extends Fruit {}

class Jonathan extends Apple {}

class Orange extends Fruit {}

public class CovariantArrays {

    @Test
    public void main(){

        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();
        fruits[1] = new Jonathan();

        try {
            fruits[0] = new Fruit();        //都会报错
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            fruits[0] = new Orange();       //都会报错
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void listTest(){
        //具有任何从Fruit继承的类型的列表
        List<? extends Fruit> fruitList = new ArrayList<>();
        //fruitList.add(new Fruit());     //都不能添加
        //fruitList.add(new Apple());
        //fruitList.add(new Object());

        fruitList.add(null);
        Fruit fruit = fruitList.get(0);     //能返回Fruit类型的

    }


}

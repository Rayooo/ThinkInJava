package typeinfo.toy;

import org.junit.Test;

/**
 * Create by Ray on 2017/4/16 19:11
 */

interface HasBatteries {}
interface Waterproof {}
interface Shoots {}

class ToySuper {}

class Toy extends ToySuper {

    Toy() {}

    Toy(int i){}

}

class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots{

    FancyToy() {super(1);}

}

public class ToyTest {

    private static void printInfo(Class cc){
        System.out.println("Class name: " + cc.getName());
        System.out.println("Simple name: " + cc.getSimpleName());
        System.out.println("Canonical name: " + cc.getCanonicalName());
    }

    @Test
    public void main(){
        Class c = null;
        try {
            c = Class.forName("typeinfo.toy.FancyToy");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        printInfo(c);

        for(Class face : c.getInterfaces()){
            printInfo(face);
        }

        Class up = c.getSuperclass();
        Object obj = null;

        try {
            obj = up.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (obj != null) {
            printInfo(obj.getClass());
        }

        Class upUp = up.getSuperclass();
        printInfo(upUp);
    }

}

/*

Class name: typeinfo.toy.FancyToy
Simple name: FancyToy
Canonical name: typeinfo.toy.FancyToy
Class name: typeinfo.toy.HasBatteries
Simple name: HasBatteries
Canonical name: typeinfo.toy.HasBatteries
Class name: typeinfo.toy.Waterproof
Simple name: Waterproof
Canonical name: typeinfo.toy.Waterproof
Class name: typeinfo.toy.Shoots
Simple name: Shoots
Canonical name: typeinfo.toy.Shoots
Class name: typeinfo.toy.Toy
Simple name: Toy
Canonical name: typeinfo.toy.Toy
Class name: typeinfo.toy.ToySuper
Simple name: ToySuper
Canonical name: typeinfo.toy.ToySuper

* */
















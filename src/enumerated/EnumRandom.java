package enumerated;

import java.util.Random;

/**
 * Created by Ray on 2017/4/4.
 */
public class EnumRandom {

    private static Random rand = new Random(47);
//    <T extends Enum<T> > 表示T是一个enum实例
    public static <T extends Enum<T> > T random(Class<T> ec){
        return random(ec.getEnumConstants());
    }

    public static <T extends Enum<T> > T random(T[] values){
        return values[rand.nextInt(values.length)];
    }

}

enum Activity{ SITTING, LYING, STANDING, HOPPING, RUNNING, DODGING, JUMPING, FALLING, FLYING}

class RandomTest{
    public static void main(String[] args){
        for (int i = 0; i < 20; i++) {
            System.out.println(EnumRandom.random(Activity.class));

        }
    }
}
package volatileTest;

/**
 * Create by Ray on 2017/4/28 10:35
 */
public class Singleton {

    //volatile的单例模式
    private volatile static Object instance;

    private Singleton(){}

    public static Object getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Object();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args){

        for (int i = 0; i < 10; i++) {
            new Thread(Singleton::getInstance).start();
        }

    }
}








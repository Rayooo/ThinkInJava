package volatileTest;

/**
 * Create by Ray on 2017/4/28 10:35
 */
public class Singleton {

    //volatile的单例模式
    private volatile static Singleton instance;

    public static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args){
        Singleton.getInstance();
    }

}

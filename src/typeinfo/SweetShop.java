package typeinfo;

import org.junit.Test;

/**
 * Create by Ray on 2017/4/16 18:45
 */

class Candy {
    static {
        System.out.println("Loading Candy");
    }
}

class Gum {
    static {
        System.out.println("Loading Gum");
    }
}

class Cookie {
    static {
        System.out.println("Loading Cookie");
    }
}

public class SweetShop {

    @Test
    public void main(){
        System.out.println("inside main");

        new Candy();
        System.out.println("After create Candy");

        try {
            Class.forName("typeinfo.Gum");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("After Class.forName Gum");

        new Cookie();
        System.out.println("Create Cookie");


    }


}


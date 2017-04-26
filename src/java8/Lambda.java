package java8;

import org.junit.Test;

import java.util.*;

/**
 * Create By Ray 2017/4/26 10:23
 */
public class Lambda {

    @Test
    public void main(){
        List<String> names = Arrays.asList("peter", "anna", "milk", "xenia");

        names.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        names.sort((String a, String b) -> {
            return a.compareTo(b);
        });

        names.sort((o1, o2) -> o2.compareTo(o1));

        names.sort(Comparator.reverseOrder());

        System.out.println(names);
    }

}

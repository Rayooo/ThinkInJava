package container;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Ray on 2017/3/30.
 */
public class SortedSetDemo {
    public static void main(String[] args){
        SortedSet<String> sortedSet = new TreeSet<>();
        Collections.addAll(sortedSet,"one two three four five six seven eight".split(" "));
        System.out.println(sortedSet);  //[eight, five, four, one, seven, six, three, two]

        String low = sortedSet.first();
        String high = sortedSet.last();
        System.out.println(low);            //eight
        System.out.println(high);           //two

        Iterator<String> it = sortedSet.iterator();
        for(int i = 0;i <= 6; ++i){
            if(i == 3){
                low = it.next();
            }
            else if(i == 6){
                high = it.next();
            }
            else{
                it.next();
            }
        }

        System.out.println(low);
        System.out.println(high);
        System.out.println(sortedSet.subSet(low,high));     //[one, seven, six]
        System.out.println(sortedSet.headSet(high));        //[one, seven, six]
        System.out.println(sortedSet.tailSet(low));


    }
}

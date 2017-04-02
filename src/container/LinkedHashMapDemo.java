package container;

import java.util.LinkedHashMap;

/**
 * Created by Ray on 2017/4/2.
 */
public class LinkedHashMapDemo {

    public static void main(String[] args){
        LinkedHashMap<Integer,String> linkedMap = new LinkedHashMap<>(new CountingMapData(9));

        System.out.println(linkedMap);

        linkedMap = new LinkedHashMap<>(16,0.75f,true);
        linkedMap.putAll(new CountingMapData(9));
        System.out.println(linkedMap);

        for (int i = 0; i < 6; i++) {
            linkedMap.get(i);
        }

        System.out.println(linkedMap);

        linkedMap.get(0);
        System.out.println(linkedMap);

    }

}

/*

{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0}
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0}
{6=G0, 7=H0, 8=I0, 0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0}
{6=G0, 7=H0, 8=I0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 0=A0}

键值对是以插入的顺序进行遍历的，甚至LRU算法的版本也是如此，但是在LRU版本中，
在访问锅前面6个元素之后，最后三个元素被移到了队列前面，然后再一次访问0时，他就被移到最后面了


* */

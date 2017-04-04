package enumerated;

/**
 * Created by Ray on 2017/4/4.
 */

enum Shrubbery {
    GROUND, CRAWLING, HANGING
}

public class EnumClass {

    public static void main(String[] args){
        for (Shrubbery s : Shrubbery.values()){
            System.out.println(s + " ordinal: " + s.ordinal());  //ordinal 序数
            System.out.println(s.compareTo(Shrubbery.CRAWLING) + " ");
            System.out.println(s.equals(Shrubbery.CRAWLING) + " ");
            System.out.println(s == Shrubbery.CRAWLING);
            System.out.println(s.getDeclaringClass());
            System.out.println(s.name());
            System.out.println("-----------------");

        }

        for (String s : "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
            System.out.println(shrub);
        }

    }

}
/*

GROUND ordinal: 0
-1
false
false
class enumerated.Shrubbery
GROUND
-----------------
CRAWLING ordinal: 1
0
true
true
class enumerated.Shrubbery
CRAWLING
-----------------
HANGING ordinal: 2
1
false
false
class enumerated.Shrubbery
HANGING
-----------------
HANGING
CRAWLING
GROUND

* */
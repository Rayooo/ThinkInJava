package enumerated;

/**
 * Created by Ray on 2017/4/4.
 */
public enum Ozwitch {

    WEST("Miss Gulch, aka the Wicked "),
    NORTH("Glinda"),
    EAST("Wicked"),
    SOUTH("Good by inference");

    //必须带有构造方法
    private String description;

    private Ozwitch(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    //覆盖enum的方法
    @Override
    public String toString() {
        String id = name();
        String lower = id.substring(1).toLowerCase();
        return id.charAt(0) + lower;
    }

    public static void main(String[] args){
        for (Ozwitch witch : Ozwitch.values()) {
            System.out.println(witch + " : " + witch.getDescription());
            System.out.println(witch);

        }
    }

}

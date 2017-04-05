package annotations.database;

/**
 * Created by Ray on 2017/4/5.
 */
public @interface SQLInteger {

    String name() default "";

    Constraints constraints() default @Constraints;

}

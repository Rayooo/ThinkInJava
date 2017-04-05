package annotations.database;

/**
 * Created by Ray on 2017/4/5.
 */
public @interface Uniqueness {

    Constraints constraints() default  @Constraints(unique = true);

}

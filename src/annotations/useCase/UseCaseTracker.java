package annotations.useCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ray on 2017/4/4.
 */
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> cl){
        for (Method m : cl.getDeclaredMethods()) {

            // getAnnotation()方法返回指定类型的注解对象，如果被注解的方法上没有该类型的注解，则返回null值
            UseCase uc = m.getAnnotation(UseCase.class);

            if(uc != null){
                System.out.println("Found Use Case:" + uc.id() + " " + uc.description());
                useCases.remove(new Integer((uc.id())));
            }
        }
        for (int i: useCases) {
            System.out.println("Warning : missing use case - " + i);
        }

    }

    public static void main(String[] args){
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases,47,48,49,50);
        trackUseCases(useCases, PasswordUtils.class);
    }
}

/*

Found Use Case:49 New passwords can't equals previously used ones
Found Use Case:47 Password must contain at least one numeric
Found Use Case:48 no description
Warning : missing use case - 50



* */

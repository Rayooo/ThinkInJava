package java8;

import org.junit.Test;

/**
 * Create by Ray 2017/4/26 10:12
 */
interface FormulaInterface {

    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}

public class Formula{
    @Test
    public void main(){
        FormulaInterface formula = new FormulaInterface() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(16));
    }
}
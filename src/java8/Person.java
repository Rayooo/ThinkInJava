package java8;

import org.junit.Test;

/**
 * Create by Ray 2017/4/26 11:00
 */
public class Person {

    private String firstName;

    private String lastName;

    public Person() {}

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public static void main(String[] args){
        PersonFactory<Person> personPersonFactory = Person::new;
        Person person = personPersonFactory.create("Ray","chen");
        System.out.println(person);
    }
}

interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}


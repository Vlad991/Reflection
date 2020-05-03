package com.infopulse;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class UsingAnnotations {
    public static void main(String[] args) {
        UsingAnnotations main = new UsingAnnotations();
        Person person = new Person();
        try {
            main.print(person, person.getClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    void print(Object o, Class c) throws IllegalAccessException {
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation.annotationType().equals(Show.class)) {
                    System.out.println(field.get(o));
                }
            }
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface Show {
    boolean value() default true;
}

class Person {
    @Show
    String name = "Max";
    int age = 18;
}

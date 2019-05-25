package com.infopulse;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value();
}

@MyAnnotation(value = "value1")
class A {
    private Integer a;

    public A() {
        this.a = 0;
    }

    public A(int aa) {
        this.a = aa;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }
}

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
//        A pa = new A();
//        Class c1 = pa.getClass();
//        Class c2 = A.class;
        Class c3 = Class.forName("com.infopulse.A");
        //c1==c2==c3
        //System.out.println(c1);
        Field[] fields = c3.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getName() + ":" + f.getType().toString());
        }

        Method[] methods = c3.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(Modifier.toString(m.getModifiers()) + " " + m.getName());
        }

        A pa = (A) c3.newInstance();
        Field fieldA = c3.getDeclaredField("a");
        fieldA.setAccessible(true);
        fieldA.set(pa, 300);
        System.out.println(pa.getA());

        Method setAmethod = c3.getMethod("setA", Integer.class);
        setAmethod.invoke(pa, new Integer(600));
        System.out.println(pa.getA());

        Constructor constructor = c3.getDeclaredConstructor(int.class);
        A pa1 = (A) constructor.newInstance(new Integer(900));
        System.out.println(pa1.getA());

        MyAnnotation annotation = (MyAnnotation)c3.getAnnotation(MyAnnotation.class);
        System.out.println(annotation.value());
        
    }
}

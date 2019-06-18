package com.infopulse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String value();
}

interface InterfaceA {
    public Integer getA();

    public void setA(Integer a);
}


//@MyAnnotation
@MyAnnotation(value = "value1")
class A implements InterfaceA {
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
        System.out.println("setA");
        this.a = a;
    }
}

class CustomInvocationHandler implements InvocationHandler {
    //    private A original;
    private Object original;

    //    public CustomInvocationHandler(A original) {
//        this.original = original;
//    }
    CustomInvocationHandler(Object original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("yes");

        return method.invoke(original, args);
    }
}

class FactoryA {
    //    private A pa;
    private InterfaceA pa;

    public FactoryA() {
        Class classA = A.class;
        MyAnnotation annotation = (MyAnnotation) classA.getAnnotation(MyAnnotation.class);
        A original = new A();
//        CustomInvocationHandler invovocationHandler = new CustomInvocationHandler(original);
//        if (annotation != null) {
//            pa = (A) Proxy.newProxyInstance(null, classA.getInterfaces(), invovocationHandler);
//        }
        if (annotation != null) {
            CustomInvocationHandler invocationHandler = new CustomInvocationHandler(original);
            pa = (InterfaceA) Proxy.newProxyInstance(classA.getClassLoader(), classA.getInterfaces(), invocationHandler);
        } else {
            pa = original;
        }
    }

//    public A getA() {}

    public InterfaceA getA() {
        return pa;
    }
}

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
//        A pa = new A();
//        Class c1 = pa.getClass();
//        Class c2 = A.class;
        Class c3 = Class.forName("com.infopulse.A");
        //c1==c2==c3
        System.out.println("/////////////////////////////////////////////");
        System.out.println(c3);
        Field[] fields = c3.getDeclaredFields();
        System.out.println("");
        System.out.println("/////////////////////////////////////////////");
        System.out.println("class A fields: ");
        for (Field f : fields) {
            System.out.println(f.getName() + ":" + f.getType().toString());
        }

        Method[] methods = c3.getDeclaredMethods();
        System.out.println("");
        System.out.println("class A methods: ");
        for (Method m : methods) {
            System.out.println(Modifier.toString(m.getModifiers()) + " " + m.getName());
        }
        System.out.println("/////////////////////////////////////////////");

        A pa = (A) c3.newInstance();
        Field fieldA = c3.getDeclaredField("a");
        fieldA.setAccessible(true);   //теперь можем менять приватное поле
        fieldA.set(pa, 300);
        System.out.println(pa.getA());

        Method setAmethod = c3.getMethod("setA", Integer.class);
        setAmethod.invoke(pa, new Integer(600));   //выполнить метод setA()
        System.out.println(pa.getA());

        Constructor constructor = c3.getDeclaredConstructor(int.class);
        A pa1 = (A) constructor.newInstance(new Integer(900));
        System.out.println(pa1.getA());

//        MyAnnotation annotation = (MyAnnotation) c3.getAnnotation(MyAnnotation.class);
//        System.out.println(annotation.value());

        FactoryA factoryA = new FactoryA();
        InterfaceA paa = factoryA.getA();
        paa.setA(400);

    }
}

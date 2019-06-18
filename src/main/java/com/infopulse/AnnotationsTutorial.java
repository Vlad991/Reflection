package com.infopulse;

import java.beans.Transient;
import java.lang.annotation.*;
import java.lang.ref.Reference;

public class AnnotationsTutorial {
    public static void main(String[] args) {

    }
}


//@MyAnn(value = "some", i = 5)
//@MyAnn("some")
@BugReport(reportedBy = "one", ref = @Reference)
class MyClass {
    @Deprecated
    int i;

    @Deprecated
    MyClass() {

    }

    @Transient
    @Deprecated
    public void method(@Deprecated int i) {
        @Deprecated
        int j;
    }
}

class MyClass2 extends MyClass {

}

@Inherited      //теперь все наследники класса MyClass тоже буду помечены аннотацией MyAnn
@Retention(RetentionPolicy.RUNTIME)  //где будет видна @MyAnn
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})//к чему могу применить эту аннотацию
@interface MyAnn {
    boolean run() default true;

    int i() default 1;

    String value();
}


@interface BugReport {
    enum Status {UCONFIRMED, CONFIRMED, FIXED, NOTABUG}

    boolean showStopper() default false;

    String assignedTo() default "[none]";

    String assigned2To() default "";

    int i() default 1 + 1;

    Class<?> testCase() default Void.class;

    BugReport.Status status() default BugReport.Status.UCONFIRMED;

    Reference ref() default @Reference();

    String[] reportedBy();

}














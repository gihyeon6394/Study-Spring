package com.tob.part6;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ReflectionTest {

    @Test
    public void invokeMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = "karina";

        assertThat(name.length(), is(6));

        Method longestMethod = String.class.getMethod("length");
        assertThat((Integer) longestMethod.invoke(name), is(6));
    }

    @Test
    public void simpleProxy() {
        Hello hello = new HelloUppercase(new HelloTarget());
        // assertThat(hello.sayHello("karina"), is("Hello karina"));
        // assertThat(hello.sayHi("karina"), is("Hi karina"));
        // assertThat(hello.sayThankYou("karina"), is("Thank you karina"));
        assertThat(hello.sayHello("karina"), is("HELLO KARINA"));
        assertThat(hello.sayHi("karina"), is("HI KARINA"));
        assertThat(hello.sayThankYou("karina"), is("THANK YOU KARINA"));

    }
}

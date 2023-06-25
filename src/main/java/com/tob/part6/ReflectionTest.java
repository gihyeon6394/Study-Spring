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
        assertThat(hello.sayHello("karina"), is("HELLO KARINA"));
        assertThat(hello.sayHi("karina"), is("HI KARINA"));
        assertThat(hello.sayThankYou("karina"), is("THANK YOU KARINA"));
    }

    @Test
    public void dynamicProxy() {
        Hello proxiedHello = (Hello) java.lang.reflect.Proxy.newProxyInstance(getClass().getClassLoader(), // 동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
                new Class[]{Hello.class}, // 구현할 인터페이스
                new UppercaseHandler(new HelloTarget())); // 부가기능과 위임 코드를 담은 InvocationHandler
        assertThat(proxiedHello.sayHello("karina"), is("HELLO KARINA"));
        assertThat(proxiedHello.sayHi("karina"), is("HI KARINA"));
        assertThat(proxiedHello.sayThankYou("karina"), is("THANK YOU KARINA"));
    }
}

package com.tob.part6;

public class HelloUppercase implements Hello {

    private Hello hello; // DI 받을 타겟 오브젝트

    public HelloUppercase(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase(); // 위임과 부가기능 적용
    }

    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase(); // 위임과 부가기능 적용
    }

    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase(); // 위임과 부가기능 적용
    }
}

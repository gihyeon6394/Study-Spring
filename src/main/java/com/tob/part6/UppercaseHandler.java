package com.tob.part6;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    Object target; // 타겟 오브젝트

    public UppercaseHandler(Hello target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object ret =  method.invoke(target, objects); // 위임
        if(ret instanceof String){
            return ((String) ret).toUpperCase(); // 부가기능 적용
        }
        else{
            return ret;
        }
    }
}

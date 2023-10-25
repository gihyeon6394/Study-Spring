package com.tob.part6.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean {
    String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Object getObject() throws Exception {
        return Message.newMessage(this.text); // 빈으로 사용할 오브젝트 생성
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        return false; // 스프링의 빈은 싱글톤, Message Object 자체는 싱글톤이 아님
    }
}

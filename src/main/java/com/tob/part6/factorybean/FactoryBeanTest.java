package com.tob.part6.factorybean;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class FactoryBeanTest {

    @Autowired
    private ApplicationContext ac;

    @Test
    @DisplayName("FactoryBean Test")
    public void getMsgFromFactoryBean() {
        Object message = ac.getBean("message");
        assertThat(message.getClass(), is(Message.class)); // message 오브젝트는 Message 타입이다 (MessageFactoryBean이 아님)
        assertThat(((Message) message).getText(), is("This is from FactoryBean")); // message 오브젝트의 text는 "Factory Bean"이다
    }

    @Test
    @DisplayName("get Factory Bean")
    public void getFactoryBean() {
        Object factory = ac.getBean("&message");
        assertThat(factory.getClass(), is(MessageFactoryBean.class));
    }
}

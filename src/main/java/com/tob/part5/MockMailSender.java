package com.tob.part5;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
import java.util.List;

public class MockMailSender implements MailSender {

    private String host;

    private List<String> requests = new ArrayList<>();

    public List<String> getRequests() {
        return requests;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        // 아무것도 못하게함
        requests.add(simpleMessage.getTo()[0]);
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        // 아무것도 못하게함

    }

    public void setHost(String host) {
        this.host = host;
    }

}

package com.tob.part5;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DumpMailSender implements MailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        // 아무것도 못하게함
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        // 아무것도 못하게함

    }

}

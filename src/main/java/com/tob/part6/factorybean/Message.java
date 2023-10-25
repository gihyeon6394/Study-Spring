package com.tob.part6.factorybean;

public class Message {

    String text;

    /**
     * 빈 생성 막음
     */
    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    /**
     * 팩토리 메소드
     */
    public static Message newMessage(String text) {
        return new Message(text);
    }
}

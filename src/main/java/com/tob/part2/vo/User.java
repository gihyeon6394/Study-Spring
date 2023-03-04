package com.tob.part2.vo;

public class User {

    String seq;
    String name;

    public User() {

    }

    public User(String seq, String name) {
        this.seq = seq;
        this.name = name;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq='" + seq + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

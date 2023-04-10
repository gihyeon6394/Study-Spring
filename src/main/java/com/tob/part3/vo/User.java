package com.tob.part3.vo;

public class User {

    int seq;
    String name;

    String nameGroup;

    public User() {

    }

    public User(int seq, String name, String nameGroup) {
        this.seq = seq;
        this.name = name;
        this.nameGroup = nameGroup;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq='" + seq + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

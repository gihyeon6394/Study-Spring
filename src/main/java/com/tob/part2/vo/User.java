package com.tob.part2.vo;

public class User {

    String seq;
    String name;

    String nameGroup;

    public User() {

    }

    public User(String seq, String name, String nameGroup) {
        this.seq = seq;
        this.name = name;
        this.nameGroup = nameGroup;
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

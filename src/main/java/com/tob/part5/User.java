package com.tob.part5;

import java.util.Objects;

public class User {

    int seq;
    String name;

    String nameGroup;

    Level level;

    public User() {

    }

    public User(int seq, String name, String nameGroup, Level level) {
        this.seq = seq;
        this.name = name;
        this.nameGroup = nameGroup;
        this.level = level;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq='" + seq + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return seq == user.seq && name.equals(user.getName()) && nameGroup.equals(user.nameGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, name, nameGroup);
    }
}

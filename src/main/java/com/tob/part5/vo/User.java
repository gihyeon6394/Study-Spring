package com.tob.part5.vo;

import java.util.Objects;

public class User {

    private int seq;
    private String name;

    private String nameGroup;

    private Level level;

    private int cntLogin; // 로그인 횟수

    private  int cntRecommend; // 추천 횟수


    public static class Builder {
        private int seq;
        private String name;
        private String nameGroup;
        private Level level;

        private int cntLogin;

        private int cntRecommend;

        public Builder() {
        }

        public Builder seq(int seq) {
            this.seq = seq;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nameGroup(String nameGroup) {
            this.nameGroup = nameGroup;
            return this;
        }

        public Builder level(Level level) {
            this.level = level;
            return this;
        }

        public Builder cntLogin(int cntLogin) {
            this.cntLogin = cntLogin;
            return this;
        }

        public Builder cntRecommend(int cntRecommend) {
            this.cntRecommend = cntRecommend;
            return this;
        }


        public User build() {
            return new User(this);
        }
    }

    public int getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public Level getLevel() {
        return level;
    }

    public int getCntLogin() {
        return cntLogin;
    }

    public int getCntRecommend() {
        return cntRecommend;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public User(Builder builder) {
        this.seq = builder.seq;
        this.name = builder.name;
        this.nameGroup = builder.nameGroup;
        this.level = builder.level;
        this.cntLogin = builder.cntLogin;
        this.cntRecommend = builder.cntRecommend;
    }

    @Override
    public String toString() {
        return "User{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", nameGroup='" + nameGroup + '\'' +
                ", level=" + level +
                ", cntLogin=" + cntLogin +
                ", cntRecommend=" + cntRecommend +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (seq != user.seq) return false;
        if (cntLogin != user.cntLogin) return false;
        if (cntRecommend != user.cntRecommend) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(nameGroup, user.nameGroup)) return false;
        return level == user.level;
    }

    @Override
    public int hashCode() {
        int result = seq;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nameGroup != null ? nameGroup.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + cntLogin;
        result = 31 * result + cntRecommend;
        return result;
    }
}

package com.niraj53.Junittesting.tddtesting.dto;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String desc;

    public User(){}

    public User(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getName().equals(user.getName()) &&
                getDesc().equals(user.getDesc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDesc());
    }
}

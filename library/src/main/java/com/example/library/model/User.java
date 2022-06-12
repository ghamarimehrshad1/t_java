package com.example.library.model;

public class User {

    private String full_name;
    private String national_code;
    private String father_name;
    private String address;

    public User(){}
    public User(String full_name, String national_code, String father_name, String address) {
        this.full_name = full_name;
        this.national_code = national_code;
        this.father_name = father_name;
        this.address = address;
    }

    public String getFull_name() {
        return full_name;
    }

    public User setFull_name(String full_name) {
        this.full_name = full_name;
        return this;
    }

    public String getNational_code() {
        return national_code;
    }

    public User setNational_code(String national_code) {
        this.national_code = national_code;
        return this;
    }

    public String getFather_name() {
        return father_name;
    }

    public User setFather_name(String father_name) {
        this.father_name = father_name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }
}

package com.example.library.model;

public class Book {
    private Integer id;
    private String name;
    private String author;
    private String publication_name;
    private Integer circulation;
    private Integer publishing_turn;
    private Integer price;
    private String admin_username;

    private String user;

    public Book(){}
    public Book(
            String name,
            String author,
            String publication_name,
            Integer circulation,
            Integer publishing_turn,
            Integer price
    ) {
        this.name = name;
        this.author = author;
        this.publication_name = publication_name;
        this.circulation = circulation;
        this.publishing_turn = publishing_turn;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public Book setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getPublication_name() {
        return publication_name;
    }

    public Book setPublication_name(String publication_name) {
        this.publication_name = publication_name;
        return this;
    }

    public Integer getCirculation() {
        return circulation;
    }

    public Book setCirculation(Integer circulation) {
        this.circulation = circulation;
        return this;
    }

    public Integer getPublishing_turn() {
        return publishing_turn;
    }

    public Book setPublishing_turn(Integer publishing_turn) {
        this.publishing_turn = publishing_turn;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Book setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getAdmin_username() {
        return admin_username;
    }

    public Book setAdmin_username(String admin_username) {
        this.admin_username = admin_username;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Book setUser(String user) {
        this.user = user;
        return this;
    }
}

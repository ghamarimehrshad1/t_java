package com.example.library.model;

import java.sql.Date;

public class UserBook {
    private Integer id;
    private Integer book_id;
    private String national_code;
    private Date borrowing_date;

    public UserBook() {}
    public UserBook(Integer book_id, String national_code) {
        this.book_id = book_id;
        this.national_code = national_code;
        this.borrowing_date = new Date(new java.util.Date().getTime());
    }

    public Integer getId() {
        return id;
    }

    public UserBook setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public UserBook setBook_id(Integer book_id) {
        this.book_id = book_id;
        return this;
    }

    public String getNational_code() {
        return national_code;
    }

    public UserBook setNational_code(String national_code) {
        this.national_code = national_code;
        return this;
    }

    public Date getBorrowing_date() {
        return borrowing_date;
    }

    public UserBook setBorrowing_date(Date borrowing_date) {
        this.borrowing_date = borrowing_date;
        return this;
    }
}

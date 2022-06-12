package com.example.library.exception;

public class BookAlreadyBorrowedException extends Exception {
    public BookAlreadyBorrowedException(){
        super();
    }
    public BookAlreadyBorrowedException(String message){
        super(message);
    }
}

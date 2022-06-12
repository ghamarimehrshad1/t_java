package com.example.library.exception;

public class UserCanNotBorrowBookException extends Exception {
    public UserCanNotBorrowBookException(){
        super();
    }
    public UserCanNotBorrowBookException(String message){
        super(message);
    }
}

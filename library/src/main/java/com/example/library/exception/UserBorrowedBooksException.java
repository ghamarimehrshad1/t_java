package com.example.library.exception;

public class UserBorrowedBooksException extends Exception{
    public UserBorrowedBooksException(){
        super();
    }
    public UserBorrowedBooksException(String message){
        super(message);
    }
}

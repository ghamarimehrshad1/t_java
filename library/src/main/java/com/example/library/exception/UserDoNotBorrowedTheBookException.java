package com.example.library.exception;

public class UserDoNotBorrowedTheBookException extends Exception{
    public UserDoNotBorrowedTheBookException(){
        super();
    }
    public UserDoNotBorrowedTheBookException(String message){
        super(message);
    }
}

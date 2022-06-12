package com.example.library.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){
        super();
    }
    public UserNotFoundException(String message){
        super(message);
    }
}

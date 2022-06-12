package com.example.library.exception;

public class UsernameAlreadyTakenException extends Exception {
    public UsernameAlreadyTakenException(){
        super();
    }
    public UsernameAlreadyTakenException(String message){
        super(message);
    }
}

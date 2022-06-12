package com.example.library.exception;

public class NationalCodeAlreadyTakenException extends Exception {
    public NationalCodeAlreadyTakenException(){
        super();
    }
    public NationalCodeAlreadyTakenException(String message){
        super(message);
    }
}

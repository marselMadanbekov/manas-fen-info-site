package com.example.manasfen.exceptions;


public class InvalidDataFormatException extends Exception {
    private final String message;
    public InvalidDataFormatException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}

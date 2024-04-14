package org.solo.exceptions;

public class InvalidPhoneNumber extends RuntimeException{
    public InvalidPhoneNumber(String message){
        super(message);
    }
}

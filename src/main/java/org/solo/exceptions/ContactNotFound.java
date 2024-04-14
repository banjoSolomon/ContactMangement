package org.solo.exceptions;

public class ContactNotFound extends RuntimeException{
    public ContactNotFound(String message){
        super(message);
    }
}

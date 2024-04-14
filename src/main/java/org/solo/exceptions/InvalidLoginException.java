package org.solo.exceptions;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message){
        super(message);
    }
}

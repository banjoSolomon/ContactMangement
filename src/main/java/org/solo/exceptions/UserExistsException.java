package org.solo.exceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message){
        super(message);
    }
}

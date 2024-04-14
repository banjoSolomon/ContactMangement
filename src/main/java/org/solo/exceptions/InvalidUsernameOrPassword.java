package org.solo.exceptions;

public class InvalidUsernameOrPassword extends RuntimeException{
    public InvalidUsernameOrPassword(String message){
        super(message);
    }
}

package org.solo.exceptions;

public class RegistrationCantBeEmpty extends RuntimeException{
    public RegistrationCantBeEmpty(String message){
        super(message);
    }
}

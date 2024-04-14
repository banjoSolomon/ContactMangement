package org.solo.response;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String username;
    private String id;
    private String dateRegistered;

}

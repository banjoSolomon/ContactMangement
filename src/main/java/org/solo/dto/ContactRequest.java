package org.solo.dto;

import lombok.Data;

@Data
public class ContactRequest {
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
}

package org.solo.response;

import lombok.Data;

@Data
public class ContactResponse {
    private String name;
    private String phoneNumber;
    private String email;
    private String id;
    private String dateCreated;
}

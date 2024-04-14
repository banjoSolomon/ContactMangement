package org.solo.response;

import lombok.Data;

@Data
public class EditContactResponse {
    private String name;
    private String phoneNumber;
    private String email;
    private String contactId;
    private String dateCreated;
}

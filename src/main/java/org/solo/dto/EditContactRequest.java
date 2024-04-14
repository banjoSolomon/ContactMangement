package org.solo.dto;

import lombok.Data;

@Data
public class EditContactRequest {
    private String author;
    private String name;
    private String phoneNumber;
    private String email;
    private String contactId;
}

package org.solo.response;

import lombok.Data;

@Data
public class ViewContactResponse {
    private String name;
    private String phoneNumber;
    private String email;
    private String dateCreated;
}

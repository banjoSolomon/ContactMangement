package org.solo.response;

import lombok.Data;

@Data
public class ShareContactResponse {
    private String contactId;
    private String name;
    private String phoneNumber;
    private String dateUpdated;


}

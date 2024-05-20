package org.solo.dto;

import lombok.Data;

@Data
public class SendMessageRequest {
    private String username;
    private String contactId;
    private String message;
    private String subject;
    private String receiver;

}

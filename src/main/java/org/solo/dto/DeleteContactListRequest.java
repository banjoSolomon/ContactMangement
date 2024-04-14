package org.solo.dto;

import lombok.Data;

@Data
public class DeleteContactListRequest {
    private String username;
    private String contactId;

}

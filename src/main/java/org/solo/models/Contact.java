package org.solo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@Document("Contact")

public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    @Id
    private String id;
    private LocalDateTime dateCreation = LocalDateTime.now();
}

package org.solo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Contact")

public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String author;
    private List<User> users = new ArrayList<>();
    @Id
    private String id;
    private LocalDateTime dateCreation = LocalDateTime.now();
}

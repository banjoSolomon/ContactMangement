package org.solo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("User")
@NoArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private List<Contact> contacts = new ArrayList<>();
    @Id
    private String id;
    private LocalDateTime dateRegistered = LocalDateTime.now();

}

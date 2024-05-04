package org.solo.utilities;

import org.solo.dto.ContactRequest;
import org.solo.dto.EditContactRequest;
import org.solo.dto.RegisterRequest;
import org.solo.models.Contact;
import org.solo.models.User;
import org.solo.response.*;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        return user;
    }
    public static RegisterUserResponse registerResponseMap(User user) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(user.getId());
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setDateRegistered(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(user.getDateRegistered()));
        return registerUserResponse;

    }
    public static LoginUserResponse mapLoginResponse(User user) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setId(user.getId());
        loginUserResponse.setUsername(user.getUsername());
        return loginUserResponse;

    }

    public static ShareContactResponse mapShareContactResponseWith(Contact contact) {
        ShareContactResponse contactResponse = new ShareContactResponse();
        contactResponse.setContactId(contact.getId());
        contactResponse.setName(contact.getName());
        contactResponse.setPhoneNumber(contact.getPhoneNumber());
        contactResponse.setDateUpdated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(contact.getDateCreation()));
        return contactResponse;
    }

    public static LogoutUserResponse mapLogoutResponse(User user) {
        LogoutUserResponse loginUserResponse = new LogoutUserResponse();
        loginUserResponse.setUsername(user.getUsername());
        loginUserResponse.setId(user.getId());
        loginUserResponse.setMessage("Logout successful");
        return loginUserResponse;

    }
    public static ContactResponse mapCreateContactResponseWith(Contact contact) {
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setName((contact.getName()));
        contactResponse.setEmail((contact.getEmail()));
        contactResponse.setPhoneNumber(contact.getPhoneNumber());
        contactResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(contact.getDateCreation()));
        return contactResponse;
    }
    public static Contact contactMap(ContactRequest contact) {
        Contact contact1 = new Contact();
        contact1.setName(contact.getName());
        contact1.setPhoneNumber(contact.getPhoneNumber());
        contact1.setEmail(contact.getEmail());
        return contact1;
    }
    public static Contact mapContact(EditContactRequest editContactRequest, Contact contact) {
        contact.setName(editContactRequest.getName());
        contact.setEmail(editContactRequest.getEmail());
        return contact;
    }


    public static EditContactResponse mapEditContactResponseWith(Contact contact) {
        EditContactResponse editContactResponse = new  EditContactResponse();
        editContactResponse.setContactId(contact.getId());
        editContactResponse.setName(contact.getName());
        editContactResponse.setEmail(contact.getEmail());
        editContactResponse.setPhoneNumber(contact.getPhoneNumber());
        editContactResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(contact.getDateCreation()));
        return editContactResponse;
    }

    public static ViewContactResponse mapContactListResponse(Contact contact) {
        ViewContactResponse viewContactResponse = new ViewContactResponse();
        viewContactResponse.setName(contact.getName());
        viewContactResponse.setEmail(contact.getEmail());
        viewContactResponse.setPhoneNumber(contact.getPhoneNumber());
        viewContactResponse.setDateCreated(DateTimeFormatter
                .ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a").format(contact.getDateCreation()));
        return viewContactResponse;

    }

    public static DeleteContactResponse mapDeleteContactResponseWith(Contact contact) {
        DeleteContactResponse deleteContactResponse = new DeleteContactResponse();
        deleteContactResponse.setContactId(contact.getId());
        return deleteContactResponse;
    }


}

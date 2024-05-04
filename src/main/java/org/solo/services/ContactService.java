package org.solo.services;

import org.solo.dto.ContactRequest;
import org.solo.dto.DeleteContactListRequest;
import org.solo.dto.EditContactRequest;
import org.solo.dto.ShareContactRequest;
import org.solo.models.Contact;
import org.solo.models.User;
import org.solo.response.DeleteContactResponse;
import org.solo.response.EditContactResponse;

import java.util.List;

public interface ContactService {
    Contact createContactsWith(ContactRequest contactRequest);

    EditContactResponse editContactWith(EditContactRequest editContactRequest, Contact contact);

    DeleteContactResponse deleteContactWith(DeleteContactListRequest deleteContactListRequest, Contact contact);

    List<Contact> searchContactsByName(User foundUser, String searchName);

    List<Contact> viewAllContact(User foundUser);

    Contact shareContact(ShareContactRequest shareContactRequest);
}

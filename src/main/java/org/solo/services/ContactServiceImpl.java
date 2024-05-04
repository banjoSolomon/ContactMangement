package org.solo.services;

import org.solo.dto.ContactRequest;
import org.solo.dto.DeleteContactListRequest;
import org.solo.dto.EditContactRequest;
import org.solo.dto.ShareContactRequest;
import org.solo.models.Contact;
import org.solo.models.User;
import org.solo.repository.ContactRepository;
import org.solo.response.DeleteContactResponse;
import org.solo.response.EditContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.solo.utilities.Mapper.*;

@Service
public class ContactServiceImpl implements ContactService{
    @Autowired
    ContactRepository contactRepository;
    @Override
    public Contact createContactsWith(ContactRequest contactRequest) {
        Contact contact = contactMap(contactRequest);
        return contactRepository.save(contact);

    }

    @Override
    public EditContactResponse editContactWith(EditContactRequest editContactRequest, Contact contact) {
        Contact contact1 =  mapContact(editContactRequest, contact);
        contactRepository.save(contact1);
        return mapEditContactResponseWith(contact1);

    }

    @Override
    public DeleteContactResponse deleteContactWith(DeleteContactListRequest deleteContactListRequest, Contact contact) {
        contactRepository.delete(contact);
        return mapDeleteContactResponseWith(contact);
    }

    @Override
    public List<Contact> searchContactsByName(User foundUser, String searchName) {
        List<Contact> foundContact = new ArrayList<>();
        for (Contact contact : foundUser.getContacts())
            if (contact.getName().contains(searchName))
                foundContact.add(contact);

        return foundContact;
    }

    @Override
    public List<Contact> viewAllContact(User foundUser) {
        List<Contact> contacts = new ArrayList<>();
        contacts.addAll(foundUser.getContacts());
        return contacts;
    }

    @Override
    public Contact shareContact(ShareContactRequest shareContactRequest) {
        Contact contact = new Contact();
        contact.setName(shareContactRequest.getName());
        contact.setAuthor(shareContactRequest.getAuthor());
        contact.setEmail(shareContactRequest.getEmail());
        return contactRepository.save(contact);




    }


}

package org.solo.services;

import org.solo.dto.*;
import org.solo.exceptions.*;
import org.solo.models.Contact;
import org.solo.models.User;
import org.solo.repository.ContactRepository;
import org.solo.repository.UserRepository;
import org.solo.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.solo.utilities.Mapper.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactService contactService;
    private User authentication;


    @Override
    public RegisterUserResponse register(RegisterRequest registerRequest) {
        validate(registerRequest.getUsername());
        User newUser = map(registerRequest);
        User savedUser = userRepository.save(newUser);
        return registerResponseMap(savedUser);

    }

    @Override
    public LoginUserResponse login(LoginRequest loginRequest) {
        User foundUser = findUserBy(loginRequest.getUsername());
        if (!isPasswordIncorrect(foundUser, loginRequest.getPassword())) {
            authentication = foundUser;
            return mapLoginResponse(foundUser);
        }else
            throw new InvalidUsernameOrPassword("Invalid Username or password");
    }

    @Override
    public ContactResponse createContacts(ContactRequest contactRequest) {
        validateAuthentication();
       String phoneNumber = contactRequest.getPhoneNumber();
       String email = contactRequest.getEmail();
        if(!isValidPhoneNumber(phoneNumber))
            throw new InvalidPhoneNumber("InvalidPhoneNumber");
        if(!isValidEmail(email)){
            throw new InvalidEmailException("Invalid Email");
        }
        User foundUser = findUserBy(contactRequest.getUsername());
        if(phoneNumberExistsForUser(phoneNumber,foundUser)){
            throw new PhoneNumberAlreadyExistsException("PhoneNumberExists");
        }
       Contact newContact = contactService.createContactsWith(contactRequest);
        foundUser.getContacts().add(newContact);
        userRepository.save(foundUser);

        return mapCreateContactResponseWith(newContact);


    }

    private boolean phoneNumberExistsForUser(String phoneNumber, User foundUser) {
        for(Contact contact : foundUser.getContacts()) {
            if(phoneNumber.equals(contact.getPhoneNumber()))
                return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()) return false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);

    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        for(char check : phoneNumber.toCharArray()){
            if(!Character.isDigit(check)){
                return false;
            }
        }
       return phoneNumber.length()== 11;

    }

    @Override
    public EditContactResponse editContactWith(EditContactRequest editContactRequest) {
        validateAuthentication();
        User user = findUserBy(editContactRequest.getAuthor());
        Contact contact = findContactBy(editContactRequest.getContactId(), user);
        return contactService.editContactWith(editContactRequest, contact);

    }

    @Override
    public List<ViewContactResponse> viewAllContact(ContactRequest contactRequest) {
        validateAuthentication();
        User foundUser = findUserBy(contactRequest.getUsername());
        List<Contact> viewAllUserContacts = contactService.viewAllContact(foundUser);
        List<ViewContactResponse> contactResponseList = new ArrayList<>();
        for (Contact contact : viewAllUserContacts) {
            ViewContactResponse viewContactResponse = mapContactListResponse(contact);
            contactResponseList.add(viewContactResponse);

        }
        return contactResponseList;

    }

    @Override
    public DeleteContactResponse deleteContactWith(DeleteContactListRequest deleteContactListRequest) {
        validateAuthentication();
        User author = findUserBy(deleteContactListRequest.getUsername());
        Contact contact = findContactBy(deleteContactListRequest.getContactId(), author);
        return contactService.deleteContactWith(deleteContactListRequest, contact);



    }

    @Override
    public List<ViewContactResponse> searchContact(SearchContactRequest searchContactRequest) {
        validateAuthentication();
        User foundUser = findUserBy(searchContactRequest.getUsername());
        String searchName = searchContactRequest.getName();
        List<Contact> foundContacts = contactService.searchContactsByName(foundUser, searchName);
        List<ViewContactResponse> searchResult = new ArrayList<>();
        for (Contact contact : foundContacts) {
            ViewContactResponse viewContactResponse = mapContactListResponse(contact);
            searchResult.add(viewContactResponse);

            }
            return searchResult;
        }

    @Override
    public LogoutUserResponse logout(LogoutRequest logoutRequest) {
        User foundUser = findUserBy(logoutRequest.getUsername());
        User savedUser = userRepository.save(foundUser);
        return mapLogoutResponse(savedUser);
    }


    private Contact findContactBy(String contactId, User user) {
        for (Contact contact : user.getContacts()) {
            if (contact.getId().equals(contactId)) {
                return contact;
            }
        }
        throw new ContactNotFound("Contact not found");
    }

    private User findUserBy(String username) {
        if (userRepository == null)
            throw new IllegalStateException("User cannot be null");

        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null)
            throw new UsernameNotFoundException(String.format("%s not found", username));

        return foundUser;

    }
    private void validateAuthentication(){
        if(authentication == null){
            throw new InvalidLoginException("user must be login to perform any operation");

        }
    }

    private boolean isPasswordIncorrect(User foundUser, String password) {
        return  !foundUser.getPassword().equals(password);

    }

    private void validate(String username) {
        if (username == null || username.trim().isEmpty())
            throw new RegistrationCantBeEmpty("Registration cannot be empty");
        boolean userExists = userRepository.existsByUsername(username);
        if (userExists) throw new UserExistsException(String.format("%s username already exists", username));


    }
}

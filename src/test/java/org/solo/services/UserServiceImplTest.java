  package org.solo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.solo.dto.*;
import org.solo.exceptions.InvalidUsernameOrPassword;
import org.solo.exceptions.PhoneNumberAlreadyExistsException;
import org.solo.exceptions.UserExistsException;
import org.solo.exceptions.UsernameNotFoundException;
import org.solo.models.Contact;
import org.solo.models.User;
import org.solo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private ContactRequest contactRequest;
    private EditContactRequest editContactRequest;
    private DeleteContactListRequest deleteContactListRequest;
    private SearchContactRequest searchContactRequest;
    private LogoutRequest logoutRequest;


    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        contactRequest = new ContactRequest();
        contactRequest.setUsername("username");
        contactRequest.setName("name");
        contactRequest.setEmail("ayomidebanjo02@gmail.com");
        contactRequest.setPhoneNumber("08164556912");

        deleteContactListRequest = new DeleteContactListRequest();
        deleteContactListRequest.setUsername("username");

        editContactRequest = new EditContactRequest();
        editContactRequest.setAuthor("username");
        editContactRequest.setPhoneNumber("08164556912");
        editContactRequest.setEmail("ayomidebanjo02@gmail.com");
        editContactRequest.setName("names");

        searchContactRequest = new SearchContactRequest();
        searchContactRequest.setUsername("username");
        searchContactRequest.setName("name");

       logoutRequest = new LogoutRequest();
       logoutRequest.setUsername("username");


    }

    @Test
    public void testUserCanRegister(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void testUserCannot_RegisterTwice(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        try {
            userService.register(registerRequest);
        } catch (UserExistsException e) {
            assertThat(e.getMessage(), containsString("username already exists"));
        }
        assertThat(userRepository.count(), is(1L));

    }

    @Test
    public void testUserCanLoginWithCorrect_Password(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(userRepository.count(), is(1L));
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        var loginResponse = userService.login(loginRequest);
        assertThat(loginResponse.getId(), notNullValue());
    }

    @Test
    public void loginNonExistentUser_throwsExceptionTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existing_username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Non existent username");
        try {
            userService.login(loginRequest);
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage(), containsString("Non existent username not found"));
        }

    }
    @Test
    public void loginWithIncorrectPassword_throwsExceptionTest(){
        userService.register(registerRequest);
        loginRequest.setPassword("incorrectPassword");
        try {
            userService.login(loginRequest);
        } catch (InvalidUsernameOrPassword e) {
            assertThat(e.getMessage(), containsString("Invalid Username or password"));
        }
    }

    @Test
    public void userCanCreate_AnewContact(){
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertThat(userRepository.count(), is(1L));
        var checkUser = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getContacts().size(), is(0));
        contactRequest.setUsername(contactRequest.getUsername());
        contactRequest.setName("name");
        contactRequest.setPhoneNumber("08164556912");
        contactRequest.setEmail("ayomidebanjo02@gmail.com");
        var contactListResponse = userService.createContacts(contactRequest);
        checkUser = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getContacts().size(), is(1));
        assertThat(contactListResponse.getId(), notNullValue());
    }


    @Test
    public void testUserCanCreateContactEditContact(){
        userService.register(registerRequest);
        userService.login(loginRequest);
        contactRequest.setUsername("username");
        contactRequest.setName("name");
        contactRequest.setEmail("ayomidebanjo02@gmail.com");
        contactRequest.setPhoneNumber("08164556912");
        userService.createContacts(contactRequest);
        var foundUser = userRepository.findByUsername(registerRequest.getUsername().toLowerCase());
        var savedContact = foundUser.getContacts().getFirst();
        assertThat(savedContact.getName(), containsString("name"));
        editContactRequest.setAuthor("username");
        editContactRequest.setContactId(savedContact.getId());
        editContactRequest.setName("names");

        var editContactResponse = userService.editContactWith(editContactRequest);
        foundUser = userRepository.findByUsername(registerRequest.getUsername().toLowerCase());
        savedContact = foundUser.getContacts().getFirst();
        assertThat(savedContact.getName(), containsString("names"));
        assertThat(editContactResponse.getContactId(), notNullValue());

    }
    @Test
    public void testUserCanViewAllContact(){
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertThat(userRepository.count(), is(1L));
        var checkUser = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getContacts().size(), is(0));

        contactRequest = new ContactRequest();
        contactRequest.setUsername(registerRequest.getUsername());
        contactRequest.setName("name");
        contactRequest.setEmail("ayomidebanjo02@gmail.com");
        contactRequest.setPhoneNumber("08164556912");
        userService.createContacts(contactRequest);

        contactRequest.setName("name1");
        contactRequest.setEmail("ayomidebanjo01@gmail.com");
        contactRequest.setPhoneNumber("08164556913");
        userService.createContacts(contactRequest);
        var checkName = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(checkName.getContacts().size(), is(2));
        var contactListResponse = userService.viewAllContact(contactRequest);
        assertThat(contactListResponse, hasSize(2));
    }

    @Test
    public void testUserCanDeleteAContact() {
        userService.register(registerRequest);
        userService.login(loginRequest);
        userService.createContacts(contactRequest);
        var foundUser = userRepository.findByUsername(registerRequest.getUsername().toLowerCase());
        assertNotNull(foundUser);

        List<Contact> userContacts = foundUser.getContacts();
        assertNotNull(userContacts);
        assertFalse(userContacts.isEmpty());
        var savedContact = userContacts.getFirst();
        assertNotNull(savedContact);
        assertThat(userContacts.size(), is(1));
        deleteContactListRequest.setUsername(registerRequest.getUsername());
        deleteContactListRequest.setContactId(savedContact.getId());
        var deleteContactResponse = userService.deleteContactWith(deleteContactListRequest);
        assertThat(deleteContactResponse.getContactId(), notNullValue());
        assertThat(deleteContactResponse.getContactId(), is(savedContact.getId()));


    }
    @Test
    public void testUserCanSearchContact(){
        registerRequest.setFirstName("Solomon");
        registerRequest.setLastName("Banjo");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        userService.login(loginRequest);
        assertThat(userRepository.count(), is(1L));
        var checkUser = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getContacts().size(), is(0));
        contactRequest = new ContactRequest();
        contactRequest.setUsername(registerRequest.getUsername());
        contactRequest.setName("name");
        contactRequest.setEmail("ayomidebanjo02@gmail.com");
        contactRequest.setPhoneNumber("08164556912");
        userService.createContacts(contactRequest);
        checkUser = userRepository.findByUsername(registerRequest.getUsername());
        assertThat(checkUser.getContacts().size(), is(1));
        var contactListResponse = userService.searchContact(searchContactRequest);
        assertThat(contactListResponse, hasSize(1));
        assertThat(contactListResponse.getFirst().getName(), containsString("name"));
        assertThat(contactListResponse.getFirst().getPhoneNumber(), containsString("08164556912"));
        assertThat(contactListResponse.getFirst().getEmail(), containsString("ayomidebanjo02@gmail.com"));

    }
    @Test
    public void testUserCanLogout() {
        registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Solomon");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThat(userRepository.count(), is(1L));
        logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        var loginResponse = userService.logout(logoutRequest);
        assertThat(loginResponse.getUsername(), notNullValue());
    }

    }












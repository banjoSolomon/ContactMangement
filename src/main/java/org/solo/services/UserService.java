package org.solo.services;

import org.solo.dto.*;
import org.solo.response.*;

import java.util.List;

public interface UserService {
    RegisterUserResponse register(RegisterRequest registerRequest);

    LoginUserResponse login(LoginRequest loginRequest);

    ContactResponse createContacts(ContactRequest contactRequest);

    EditContactResponse editContactWith(EditContactRequest editContactRequest);

    List<ViewContactResponse> viewAllContact(ContactRequest contactRequest);

    DeleteContactResponse deleteContactWith(DeleteContactListRequest deleteContactListRequest);

    List<ViewContactResponse> searchContact(SearchContactRequest searchContactRequest);

    LogoutUserResponse logout(LogoutRequest logoutRequest);

    void shareContact(ShareContactRequest shareContactRequest);
}
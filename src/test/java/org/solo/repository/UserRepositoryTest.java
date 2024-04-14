package org.solo.repository;

import org.junit.jupiter.api.Test;
import org.solo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest(){
      userRepository.deleteAll();
        User user = new User();
        userRepository.save(user);
        assertThat(userRepository.count(), is(1L));


    }

}
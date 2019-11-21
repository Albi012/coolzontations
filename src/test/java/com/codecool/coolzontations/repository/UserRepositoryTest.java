package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveOneUser(){
        User testUser = User.builder()
                .username("TestUser")
                .level(Level.OOP)
                .build();
        userRepository.save(testUser);
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }

//    @Test(expected = DataIntegrityViolationException.class)
    public void saveUniqueFieldTwice(){
        User testUser1 = User.builder()
                .username("TestUser")
                .level(Level.OOP)
                .build();

        userRepository.save(testUser1);

        User testUser2 = User.builder()
                .username("TestUser")
                .level(Level.WEB)
                .build();

        userRepository.saveAndFlush(testUser2);
    }

    @Test
    public void userNameShouldBeNotNull(){
        User user = User.builder()
                .level(Level.WEB)
                .build();

        assertThrows(DataIntegrityViolationException.class,()->{userRepository.saveAndFlush(user);});

    }


}
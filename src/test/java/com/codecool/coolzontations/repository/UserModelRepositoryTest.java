package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class UserModelRepositoryTest {

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveOneUser(){
        UserModel testUserModel = UserModel.builder()
                .username("TestUser")
                .level(Level.OOP)
                .build();
        userModelRepository.save(testUserModel);
        List<UserModel> userModels = userModelRepository.findAll();
        assertThat(userModels).hasSize(1);
    }

//    @Test(expected = DataIntegrityViolationException.class)
    public void saveUniqueFieldTwice(){
        UserModel testUserModel1 = UserModel.builder()
                .username("TestUser")
                .level(Level.OOP)
                .build();

        userModelRepository.save(testUserModel1);

        UserModel testUserModel2 = UserModel.builder()
                .username("TestUser")
                .level(Level.WEB)
                .build();

        userModelRepository.saveAndFlush(testUserModel2);
    }

    @Test
    public void userNameShouldBeNotNull(){
        UserModel userModel = UserModel.builder()
                .level(Level.WEB)
                .build();

        assertThrows(DataIntegrityViolationException.class,()->{
            userModelRepository.saveAndFlush(userModel);});

    }


}

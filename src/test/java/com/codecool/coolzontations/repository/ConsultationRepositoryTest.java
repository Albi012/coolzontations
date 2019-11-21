package com.codecool.coolzontations.repository;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ConsultationRepositoryTest {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveOneConsultation() {
        Consultation consultation = Consultation.builder()
                .duration(30)
                .date(LocalDateTime.of(2017, 12, 21, 10, 32))
                .description("testDesc")
                .participantLimit(4)
                .build();
        consultationRepository.save(consultation);
        List<Consultation> consultations = consultationRepository.findAll();
        assertThat(consultations).hasSize(1);

    }

    //    @Test(expected = DataIntegrityViolationException.class)
    public void dateShouldBeNotNull() {
        Consultation consultation = Consultation.builder()
                .participantLimit(3)
                .description("testDesc")
                .duration(30)
                .build();

        consultationRepository.saveAndFlush(consultation);
    }

    @Test
    public void userPersistWithConsultation() {
        User testUser1 = User.builder()
                .username("TestUser1")
                .level(Level.WEB)
                .build();
        User testUser2 = User.builder()
                .username("TestUser2")
                .level(Level.OOP)
                .build();

        Consultation testConsultation = Consultation.builder()
                .participants(new HashSet<>(Arrays.asList(testUser1, testUser2)))
                .duration(30)
                .date(LocalDateTime.of(2019, 10, 12, 12, 30))
                .description("TestDesc")
                .participantLimit(10)
                .build();

        consultationRepository.save(testConsultation);
        List<User> users = userRepository.findAll();
        assertThat(users).allMatch(user -> user.getId() > 0L);

    }

    @Test
    @Transactional
    public void getUserJoinedConsultations() {
        User user = User.builder()
                .username("OptionalUser")
                .level(Level.WEB)
                .build();
        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("testDesc")
                .duration(30)
                .participantLimit(10)
                .participants(new HashSet<>(Arrays.asList(user)))
                .build();
        consultationRepository.saveAndFlush(consultation);
        testEntityManager.clear();
        assertThat(consultationRepository.findAll()).anyMatch(consultation1 -> consultation1.getParticipants().contains(user));
    }

    @Test
    public void getUserHostedConsultations() {
        User user = User.builder()
                .username("OptionalUser")
                .level(Level.WEB)
                .build();
        userRepository.saveAndFlush(user);
        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .host(user)
                .description("testDesc")
                .duration(30)
                .participantLimit(10)
                .build();
        consultationRepository.saveAndFlush(consultation);
        testEntityManager.clear();
        assertThat(consultationRepository.findAll()).anyMatch(consultation1 -> consultation1.getHost().equals(user));
    }
}
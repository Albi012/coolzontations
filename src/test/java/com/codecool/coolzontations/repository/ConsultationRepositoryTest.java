//package com.codecool.coolzontations.repository;
//
//import com.codecool.coolzontations.model.Consultation;
//import com.codecool.coolzontations.model.Level;
//import com.codecool.coolzontations.model.UserModel;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.context.ActiveProfiles;
//
//import javax.transaction.Transactional;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@ActiveProfiles("test")
//public class ConsultationRepositoryTest {
//
//    @Autowired
//    private ConsultationRepository consultationRepository;
//
//    @Autowired
//    private UserModelRepository userModelRepository;
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @BeforeEach
//    public void clear(){
//        userModelRepository.deleteAll();
//        consultationRepository.deleteAll();
//    }
//
//    @Test
//    public void showActiveConsultation() {
//        LocalDateTime now = LocalDateTime.now();
//        Consultation consultation = Consultation.builder()
//                .duration(30)
//                .date(LocalDateTime.of(2027, 12, 21, 10, 32))
//                .description("testDesc")
//                .participantLimit(4)
//                .build();
//        consultationRepository.save(consultation);
//        List<Consultation> consultations = consultationRepository.findActiveConsultations(now);
//        assertThat(consultations).hasSize(1);
//
//    }
//
//    @Test
//    public void showArchivedConsultation() {
//        LocalDateTime now = LocalDateTime.now();
//        Consultation consultation = Consultation.builder()
//                .duration(30)
//                .date(LocalDateTime.of(2017, 12, 21, 10, 32))
//                .description("testDesc")
//                .participantLimit(4)
//                .build();
//        consultationRepository.save(consultation);
//        List<Consultation> consultations = consultationRepository.findArchivedConsultations(now);
//        assertThat(consultations).hasSize(1);
//
//    }
//
//    @Test
//    public void dateShouldBeNotNull() {
//        Consultation consultation = Consultation.builder()
//                .participantLimit(3)
//                .description("testDesc")
//                .duration(30)
//                .build();
//
//        Assertions.assertThrows(DataIntegrityViolationException.class,
//                () -> consultationRepository.saveAndFlush(consultation));
//    }
//
//    @Test
//    public void userPersistWithConsultation() {
//        UserModel testUserModel1 = UserModel.builder()
//                .username("TestUser1")
//                .email("test1@cool.com")
//                .password("kacsa1")
//                .level(Level.WEB)
//                .build();
//        UserModel testUserModel2 = UserModel.builder()
//                .username("TestUser2")
//                .email("test2@cool.com")
//                .password("kacsa2")
//                .level(Level.OOP)
//                .build();
//
//        Consultation testConsultation = Consultation.builder()
//                .participants(new HashSet<>(Arrays.asList(testUserModel1, testUserModel2)))
//                .duration(30)
//                .date(LocalDateTime.of(2019, 10, 12, 12, 30))
//                .description("TestDesc")
//                .participantLimit(10)
//                .build();
//
//        consultationRepository.save(testConsultation);
//        List<UserModel> userModels = userModelRepository.findAll();
//        assertThat(userModels).allMatch(user -> user.getId() >= 0L);
//
//    }
//
//    @Test
//    public void getUserJoinedConsultations() {
//        UserModel userModel = UserModel.builder()
//                .username("OptionalUser")
//                .email("optuser@cool.com")
//                .password("kacsa")
//                .level(Level.WEB)
//                .build();
//        Consultation consultation = Consultation.builder()
//                .date(LocalDateTime.now())
//                .description("testDesc")
//                .duration(30)
//                .participantLimit(10)
//                .participants(new HashSet<>(Arrays.asList(userModel)))
//                .build();
//        consultationRepository.saveAndFlush(consultation);
//        testEntityManager.clear();
//        assertThat(consultationRepository.findAll())
//                .anyMatch(consultation1 -> consultation1.getParticipants().size() == 1);
//    }
//
//    @Test
//    public void getUserHostedConsultations() {
//        UserModel userModel = UserModel.builder()
//                .username("OptionalUser")
//                .email("optuser@cool.com")
//                .password("kacsa")
//                .level(Level.WEB)
//                .build();
//        userModelRepository.saveAndFlush(userModel);
//        Consultation consultation = Consultation.builder()
//                .date(LocalDateTime.now())
//                .host(userModel)
//                .description("testDesc")
//                .duration(30)
//                .participantLimit(10)
//                .build();
//        consultationRepository.saveAndFlush(consultation);
//        testEntityManager.clear();
//        assertThat(consultationRepository.findAll()).anyMatch(consultation1 -> consultation1.getHost().getUsername().equals(userModel.getUsername()));
//    }
//}

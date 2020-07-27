package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.controller.dto.RegistrationUserModel;
import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import com.codecool.coolzontations.service.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static com.codecool.coolzontations.model.Level.WEB;
import static com.codecool.coolzontations.model.Subject.CSHARP;
import static com.codecool.coolzontations.model.Subject.JAVA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ConsultationDataServiceTest {

    @Mock
    private UserModelRepository userModelRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private ConsultationDataService consultationDataService = new ConsultationDataService();

    @InjectMocks
    private UserDataService userDataService = new UserDataService();

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void successfulJoinToConsultation(){
        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("testDesc")
                .duration(30)
                .participantLimit(10)
                .participants(new HashSet<>())
                .build();

        UserModel userModel = UserModel.builder()
                .username("OptionalUser")
                .email("optuser@cool.com")
                .password("kacsa")
                .level(WEB)
                .build();


        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel));
        when(consultationRepository.findById(4L)).thenReturn(Optional.of(consultation));
        when(consultationRepository.saveAndFlush(any())).thenReturn(consultation);

        DataFromRequest data = new DataFromRequest(2L, 4L);

        Consultation response = consultationDataService.joinConsultation(data);
        assertThat(response).isNotNull();

        verify(userModelRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).saveAndFlush(any());

    }

    @Test
    void cannotJoinToConsultation(){
        UserModel userModel1 = UserModel.builder()
                .username("OptionalUser1")
                .email("optuser1@cool.com")
                .password("kacsa")
                .level(WEB)
                .build();

        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("testDesc")
                .duration(30)
                .participantLimit(1)
                .participants(Set.of(userModel1))
                .build();


        UserModel userModel2 = UserModel.builder()
                .username("OptionalUser2")
                .email("optuser2@cool.com")
                .password("kacsa")
                .level(WEB)
                .build();


        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel2));
        when(consultationRepository.findById(4L)).thenReturn(Optional.of(consultation));

        DataFromRequest data = new DataFromRequest(2L, 4L);

        assertThrows(ConsultationLimitException.class, () -> consultationDataService.joinConsultation(data));

        verify(userModelRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).findById(anyLong());

    }

    @Test
    void newConsultation(){
        UserModel userModel1 = UserModel.builder()
                .username("OptionalUser1")
                .email("optuser1@cool.com")
                .password("kacsa")
                .level(WEB)
                .build();

        Consultation mockConsultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("testDesc")
                .duration(30)
                .participantLimit(1)
                .participants(Set.of(userModel1))
                .build();


        ConsultationDataFromRequest consultation = new ConsultationDataFromRequest();
        consultation.setDate(LocalDateTime.now());
        consultation.setDescription("Test description");
        consultation.setDuration(30);
        consultation.setHostID(2L);
        consultation.setParticipantLimit(3);
        consultation.setSubjects(List.of(JAVA.name(), CSHARP.name()));

        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel1));
        when(consultationRepository.saveAndFlush(mockConsultation)).thenReturn(mockConsultation);

        assertDoesNotThrow(() -> consultationDataService.createNewConsultation(consultation));

        verify(userModelRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).saveAndFlush(any());

    }

    @Test
    void failedToCreateNewConsultation(){
        UserModel userModel1 = UserModel.builder()
                .username("OptionalUser1")
                .email("optuser1@cool.com")
                .password("kacsa")
                .level(WEB)
                .build();

        ConsultationDataFromRequest consultation = new ConsultationDataFromRequest();
        consultation.setHostID(2L);

        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel1));

        assertThrows(ConsultationNotCreatedException.class, () -> consultationDataService.createNewConsultation(consultation));

        verify(userModelRepository, times(1)).findById(anyLong());

    }

    @Test
    void userRegistration(){
        RegistrationUserModel registrationUserModel = new RegistrationUserModel();
        registrationUserModel.setEmail("newuser@user.com");
        registrationUserModel.setLevel(WEB);
        registrationUserModel.setPassword1("asd1");
        registrationUserModel.setPassword2("asd1");
        registrationUserModel.setUsername("user");

        when(userModelRepository.existsByUsername(anyString())).thenReturn(false);
        when(userModelRepository.existsByEmail(anyString())).thenReturn(false);
        when(userModelRepository.save(any())).thenReturn(any());

        assertDoesNotThrow(() -> userDataService.userReg(registrationUserModel));

        verify(userModelRepository, times(1)).existsByEmail(anyString());
        verify(userModelRepository, times(1)).existsByUsername(anyString());
        verify(userModelRepository, times(1)).save(any());

    }

    @Test
    void userRegistrationFailedBecauseUsernameAlreadyExist(){
        RegistrationUserModel registrationUserModel = new RegistrationUserModel();
        registrationUserModel.setEmail("newuser@user.com");
        registrationUserModel.setLevel(WEB);
        registrationUserModel.setPassword1("asd1");
        registrationUserModel.setPassword2("asd1");
        registrationUserModel.setUsername("user");

        when(userModelRepository.existsByUsername(anyString())).thenReturn(true);
        when(userModelRepository.existsByEmail(anyString())).thenReturn(false);
        when(userModelRepository.save(any())).thenReturn(any());

        assertThrows(UsernameIsTakenException.class, () -> userDataService.userReg(registrationUserModel));

        verify(userModelRepository, times(1)).existsByUsername(anyString());
        verify(userModelRepository, times(0)).existsByEmail(anyString());
        verify(userModelRepository, times(0)).save(any());

    }

    @Test
    void userRegistrationFailedBecauseEmailAlreadyExist(){
        RegistrationUserModel registrationUserModel = new RegistrationUserModel();
        registrationUserModel.setEmail("newuser@user.com");
        registrationUserModel.setLevel(WEB);
        registrationUserModel.setPassword1("asd1");
        registrationUserModel.setPassword2("asd1");
        registrationUserModel.setUsername("user");

        when(userModelRepository.existsByUsername(anyString())).thenReturn(false);
        when(userModelRepository.existsByEmail(anyString())).thenReturn(true);
        when(userModelRepository.save(any())).thenReturn(any());

        assertThrows(EmailAlreadyRegisteredException.class, () -> userDataService.userReg(registrationUserModel));

        verify(userModelRepository, times(1)).existsByUsername(anyString());
        verify(userModelRepository, times(1)).existsByEmail(anyString());
        verify(userModelRepository, times(0)).save(any());

    }

    @Test
    void cancelConsultation(){
        when(consultationRepository.findById(anyLong())).thenReturn(Optional.of(new Consultation()));
        doNothing().when(consultationRepository).delete(any());

        consultationDataService.cancelConsultation(2L);

        verify(consultationRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).delete(any());

    }

    @Test
    void failedToCancelConsultation(){
        when(consultationRepository.findById(anyLong())).thenThrow(ConsultationNotFoundException.class);
        doNothing().when(consultationRepository).delete(any());

        assertThrows(ConsultationNotFoundException.class, () -> consultationDataService.cancelConsultation(2L));

        verify(consultationRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(0)).delete(any());

    }
}
package com.codecool.coolzontations.service;

import com.codecool.coolzontations.controller.dto.ConsultationDataFromRequest;
import com.codecool.coolzontations.controller.dto.DataFromRequest;
import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static com.codecool.coolzontations.model.Subject.CSHARP;
import static com.codecool.coolzontations.model.Subject.JAVA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DataMangerTest {

    @Mock
    private UserModelRepository userModelRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private DataManger dataManger = new DataManger();

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
                .level(Level.WEB)
                .build();


        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel));
        when(consultationRepository.findById(4L)).thenReturn(Optional.of(consultation));
        when(consultationRepository.saveAndFlush(any())).thenReturn(consultation);

        DataFromRequest data = new DataFromRequest(2L, 4L);

        ResponseEntity response = dataManger.joinConsultation(data);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

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
                .level(Level.WEB)
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
                .level(Level.WEB)
                .build();


        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel2));
        when(consultationRepository.findById(4L)).thenReturn(Optional.of(consultation));

        DataFromRequest data = new DataFromRequest(2L, 4L);

        ResponseEntity response = dataManger.joinConsultation(data);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);

        verify(userModelRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).findById(anyLong());

    }

    @Test
    void newConsultation(){
        UserModel userModel1 = UserModel.builder()
                .username("OptionalUser1")
                .email("optuser1@cool.com")
                .password("kacsa")
                .level(Level.WEB)
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


        ResponseEntity response = dataManger.createNewConsultation(consultation);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        verify(userModelRepository, times(1)).findById(anyLong());
        verify(consultationRepository, times(1)).saveAndFlush(any());

    }

    @Test
    void failedToCreateNewConsultation(){
        UserModel userModel1 = UserModel.builder()
                .username("OptionalUser1")
                .email("optuser1@cool.com")
                .password("kacsa")
                .level(Level.WEB)
                .build();

        ConsultationDataFromRequest consultation = new ConsultationDataFromRequest();
        consultation.setHostID(2L);

        when(userModelRepository.findById(2L)).thenReturn(Optional.of(userModel1));


        ResponseEntity response = dataManger.createNewConsultation(consultation);
        assertThat(response.getStatusCodeValue()).isEqualTo(422);

        verify(userModelRepository, times(1)).findById(anyLong());

    }
}
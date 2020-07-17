package com.codecool.coolzontations.controller;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.UserModel;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public class RouteControllerTest {


    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    public void addParticipantToConsultation() throws Exception {
        UserModel optionalUserModel = UserModel.builder()
                .username("OptionalUser")
                .level(Level.WEB)
                .build();
        Consultation optionalConsultation = Consultation.builder()
                .date(LocalDateTime.now())
                .description("OptionalDesc")
                .duration(30)
                .participantLimit(10)
                .build();
        optionalUserModel = userModelRepository.saveAndFlush(optionalUserModel);
        optionalConsultation = consultationRepository.saveAndFlush(optionalConsultation);

        testEntityManager.clear();

        mvc.perform(MockMvcRequestBuilders
                .post("/joinConsultation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userID\":" + optionalUserModel.getId() + ", \"consultationID\": " + optionalConsultation.getId() + "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(consultationRepository.findAll()).anyMatch(consultation -> {
            return consultation.getParticipants().size() == 1;
        });
    }

    @Test
    @Transactional
    public void cancelConsultationParticipationAsParticipant() throws Exception {
        UserModel optionalUserModel = UserModel.builder()
                .username("OptionalUser")
                .level(Level.WEB)
                .build();
        Consultation optionalConsultation = Consultation.builder()
                .date(LocalDateTime.now())
                .participants(new HashSet<>(Arrays.asList(optionalUserModel)))
                .description("OptionalDesc")
                .duration(30)
                .participantLimit(10)
                .build();
        UserModel finalOptionalUserModel = userModelRepository.saveAndFlush(optionalUserModel);
        optionalConsultation = consultationRepository.saveAndFlush(optionalConsultation);
        testEntityManager.clear();
        mvc.perform(MockMvcRequestBuilders
                .post("/dropConsultation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userID\":" + optionalUserModel.getId() + "," +
                        " \"consultationID\": " + optionalConsultation.getId() + "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertThat(consultationRepository.findAll()).noneMatch(consultation -> consultation.getParticipants().contains(finalOptionalUserModel));
    }


    @Test
    @Transactional
    public void creatingNewConsultationFromConsultationData() throws Exception {
        UserModel userModel = UserModel.builder()
                .username("TestUser")
                .level(Level.WEB)
                .build();
        UserModel finalUserModel = userModelRepository.saveAndFlush(userModel);
        testEntityManager.clear();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", LocalDateTime.of(2019, 10, 20, 10, 30));
        jsonObject.put("subject", Arrays.asList(Subject.JAVA));
        jsonObject.put("hostID", finalUserModel.getId());
        jsonObject.put("duration", 10);
        jsonObject.put("participantLimit", 5);
        jsonObject.put("description", "TestDesc");
        System.out.println(jsonObject.toString());
        mvc.perform(MockMvcRequestBuilders
                .post("/createNewConsultation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(jsonObject))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertThat(consultationRepository.findAll()).anyMatch(consultation -> consultation.getHost().getId().equals(finalUserModel.getId()));
    }

    @Test
    @Transactional
    public void cancelConsultation() throws Exception {

        Consultation consultation = Consultation.builder()
                .date(LocalDateTime.now())
                .duration(30)
                .participantLimit(5)
                .description("apacuka teszt")
                .build();

        Consultation consi = consultationRepository.saveAndFlush(consultation);

        mvc.perform(MockMvcRequestBuilders
                .get("/cancelConsultation/" + consi.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertThat(consultationRepository.findAll()).noneMatch(consultation1 -> consultation1.getId() == null);

    }

}

package com.codecool.coolzontations;

import com.codecool.coolzontations.model.Consultation;
import com.codecool.coolzontations.model.Level;
import com.codecool.coolzontations.model.Subject;
import com.codecool.coolzontations.model.User;
import com.codecool.coolzontations.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CoolzontationsApplication {

    @Autowired
    private ConsultationRepository consultationRepository;

    public static void main(String[] args) {
        SpringApplication.run(CoolzontationsApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            User user1 = User.builder()
                    .username("Jancsi")
                    .level(Level.ADVANCE)
                    .build();
            User user2 = User.builder()
                    .username("Gyula")
                    .level(Level.PROGBASICS)
                    .build();
            User user3 = User.builder()
                    .username("Karcsi")
                    .level(Level.WEB)
                    .build();
            User user4 = User.builder()
                    .username("myUser")
                    .level(Level.WEB)
                    .build();

            Set<Subject> subjects = new HashSet<>(Arrays.asList(Subject.JAVA, Subject.JAVASCRIPT, Subject.REACT));

            Consultation c1 = Consultation.builder()
                    .date(LocalDate.now())
                    .duration(30)
                    .subjects(subjects)
                    .participantLimit(3)
                    .description("apacuka fundaluka")
                    .build();

            user1.addConsultation(c1);
            c1.addParticipant(user3);
            c1.addParticipant(user2);

            Consultation c2 = Consultation.builder()
                    .date(LocalDate.of(1990, 12, 21))
                    .duration(90)
                    .subjects(subjects)
                    .participantLimit(1)
                    .description("fundakave")
                    .build();

            user2.addConsultation(c2);
            c2.addParticipant(user1);
            c2.addParticipant(user3);

            Consultation c3 = Consultation.builder()
                    .date(LocalDate.of(2019, 11, 25))
                    .duration(120)
                    .subjects(subjects)
                    .participantLimit(2)
                    .description("ap cuk fundaluk funda kave kamanduk")
                    .build();

            user3.addConsultation(c3);
            c3.addParticipant(user1);
            c3.addParticipant(user2);

            consultationRepository.saveAll(Arrays.asList(c1, c2, c3));

        };
    }
}

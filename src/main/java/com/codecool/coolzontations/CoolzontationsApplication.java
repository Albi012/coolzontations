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
            Set<User> participants = new HashSet<>(Arrays.asList(user2, user3));
            Set<User> participants2 = new HashSet<>(Arrays.asList(user2, user1));
            Consultation c1 = Consultation.builder()
                    .date(LocalDate.now())
                    .duration(30)
                    .host(user1)
                    .subjects(subjects)
                    .participants(participants)
                    .participantLimit(3)
                    .description("apacuka fundaluka")
                    .build();
            Consultation c2 = Consultation.builder()
                    .date(LocalDate.of(1990, 12, 21))
                    .duration(90)
                    .subjects(subjects)
                    .host(user2)
                    .participants(participants)
                    .participantLimit(1)
                    .description("fundakave")
                    .build();
            Consultation c3 = Consultation.builder()
                    .date(LocalDate.of(2019, 11, 25))
                    .duration(120)
                    .subjects(subjects)
                    .host(user3)
                    .participants(participants2)
                    .participantLimit(2)
                    .description("ap cuk fundaluk funda kave kamanduk")
                    .build();

            consultationRepository.saveAll(Arrays.asList(c1, c2, c3));

        };
    }
}

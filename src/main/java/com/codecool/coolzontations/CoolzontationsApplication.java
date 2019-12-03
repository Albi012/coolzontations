package com.codecool.coolzontations;

import com.codecool.coolzontations.model.*;
import com.codecool.coolzontations.repository.ConsultationRepository;
import com.codecool.coolzontations.repository.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CoolzontationsApplication {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private UserModelRepository userModelRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(CoolzontationsApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            UserModel userModel1 = UserModel.builder()
                    .username("user")
                    .level(Level.ADVANCE)
                    .email("asd@bsd.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Roles.USER)
                    .build();


            UserModel userModel2 = UserModel.builder()
                    .username("Gyula")
                    .level(Level.PROGBASICS)
                    .email("bsd@cdf.com")
                    .password(passwordEncoder.encode("asd"))
                    .build();

            UserModel userModel3 = UserModel.builder()
                    .username("Karcsi")
                    .level(Level.WEB)
                    .email("qwe@zxc.com")
                    .password(passwordEncoder.encode("asd"))
                    .build();

            UserModel userModel4 = UserModel.builder()
                    .username("myUser")
                    .level(Level.WEB)
                    .email("wer@rew.com")
                    .password(passwordEncoder.encode("asd"))
                    .build();

            Set<Subject> subjects = new HashSet<>(Arrays.asList(Subject.JAVA, Subject.JAVASCRIPT, Subject.REACT));

            Consultation c1 = Consultation.builder()
                    .date(LocalDateTime.now())
                    .duration(30)
                    .subjects(subjects)
                    .participantLimit(4)
                    .description("apacuka fundaluka")
                    .build();

            userModel1.addConsultation(c1);
            c1.addParticipant(userModel3);
            c1.addParticipant(userModel2);

            Consultation c2 = Consultation.builder()
                    .date(LocalDateTime.of(2020, 1, 21, 10, 10))
                    .duration(90)
                    .subjects(subjects)
                    .participantLimit(2)
                    .description("fundakave")
                    .build();

            userModel2.addConsultation(c2);
            c2.addParticipant(userModel1);
            c2.addParticipant(userModel3);

            Consultation c3 = Consultation.builder()
                    .date(LocalDateTime.of(2019, 11, 25, 12, 30))
                    .duration(120)
                    .subjects(subjects)
                    .participantLimit(4)
                    .description("ap cuk fundaluk funda kave kamanduk")
                    .build();

            userModel3.addConsultation(c3);
            c3.addParticipant(userModel1);
            c3.addParticipant(userModel2);

            consultationRepository.saveAll(Arrays.asList(c1, c2, c3));
            userModelRepository.save(userModel4);


        };
    }
}

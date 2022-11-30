package com.example.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

=======
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
>>>>>>> 5b85ce4 (Initial commit)
@SpringBootApplication
public class TeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}

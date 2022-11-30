package com.example.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

=======
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
>>>>>>> 5b85ce4 (Initial commit)
@SpringBootApplication
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

}

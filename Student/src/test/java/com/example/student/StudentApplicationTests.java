package com.example.student;

import com.example.student.service.RunCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class StudentApplicationTests {
    @Autowired
    RunCode runCode;

    @Test
    void contextLoads() throws IOException, InterruptedException {
        String code = "\n" +
                "\n" +
                "print('hello,world!')";
        System.out.println(runCode.runCode("PYTHON", code).getOutput());
    }

}

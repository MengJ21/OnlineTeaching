package com.example.teacher.service.client;

import com.example.commons.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("Student")
public interface StudentClient {

    @GetMapping("/teacher/getStudent/{studentId}")
    public Student getStudent(@PathVariable String studentId);

}

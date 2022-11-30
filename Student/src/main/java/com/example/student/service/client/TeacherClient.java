package com.example.student.service.client;


import com.example.commons.entity.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("Teacher")
public interface TeacherClient {

    @GetMapping("/student/getTeacher/{teacherId}")
    public Teacher getTeacher(@PathVariable String teacherId);
    @PostMapping("/teacher/upload/file/{courseId}/{chapterId}/{experimentId}")
    public String upload(@RequestPart("file") MultipartFile multipartFile, @PathVariable String courseId, @PathVariable String chapterId, @PathVariable String experimentId);
}

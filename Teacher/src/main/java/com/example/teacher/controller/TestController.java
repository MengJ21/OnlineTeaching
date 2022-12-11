package com.example.teacher.controller;



import com.example.commons.util.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class TestController {

    @Autowired
    UploadFileUtil uploadFile;


    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile file) {
          return uploadFile.uploadFile(file);
    }
}

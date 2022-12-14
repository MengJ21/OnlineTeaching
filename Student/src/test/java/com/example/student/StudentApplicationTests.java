package com.example.student;

import com.example.student.service.IStudentService;
import com.example.student.service.OssDownloadService;
import com.example.student.service.RunCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.lang.reflect.Field;

@SpringBootTest
class StudentApplicationTests {
    @Autowired
    RunCode runCode;
    @Autowired
    IStudentService iStudentService;
    @Autowired
    OssDownloadService ossDownloadService;

    @Test
    void contextLoads() throws IOException, InterruptedException {
//        String code = "\n" +
//                "\n" +
//                "print('hello,world!')";
//        System.out.println(runCode.runCode("PYTHON", code).getOutput());
//        System.out.println(runCode.runCode("PYTHON", code).getCodeFilePath());
        String code = "#include <iostream>\n" +
                "using namespace std;\n" +
                " \n" +
                "int main() \n" +
                "{\n" +
                "    cout << \"Hello, World!\";\n" +
                "    return 0;\n" +
                "}";
        System.out.println(runCode.runCode("CPP",code).getOutput());
    }
    @Test
    void testDeleteFile() {
        File file = new File("C:\\test.txt");
        System.out.println(file.isFile());
        file.delete();
    }
    @Test
    void testGetChapterIdAndGetExperimentId() {
        String chapterId = iStudentService.findChapterIdByExperimentId("1");
        String courseId = iStudentService.findCourseIdByChapterId(chapterId);
        System.out.println("章节id：" + chapterId);
        System.out.println("课程id：" + courseId);
    }
    @Test
    void testUploadFile() throws IOException {
        String s = ossDownloadService.getFile("1course/1chapter/实验1/2022/12/14/Main.py");
        System.out.println(s);
    }
}

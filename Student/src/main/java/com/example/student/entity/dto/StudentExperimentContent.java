package com.example.student.entity.dto;

import com.example.commons.entity.StudentExperiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentExperimentContent {
    private StudentExperiment studentExperiment;
    private String codeContent;
}

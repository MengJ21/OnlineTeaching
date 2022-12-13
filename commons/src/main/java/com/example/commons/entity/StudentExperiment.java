package com.example.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_experiment")
public class StudentExperiment {
    @TableId("student_experiment_id")
    private String studentExperimentId;
    private String experimentId;
    private String studentId;
    private String experiment_content;
    private String fileUrl;
    private String fileName;
    private int score;
    private Boolean state;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getStudentExperimentId() {
        return studentExperimentId;
    }

    public void setStudentExperimentId(String studentExperimentId) {
        this.studentExperimentId = studentExperimentId;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExperiment_content() {
        return experiment_content;
    }

    public void setExperiment_content(String experiment_content) {
        this.experiment_content = experiment_content;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

package com.example.commons.entity;

public class StudentExperiment {
    private String studentExperimentId;
    private String experimentId;
    private String studentId;
    private String experiment_content;
    private String file_url;
    private String file_name;
    private int score;
    private String state;

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

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

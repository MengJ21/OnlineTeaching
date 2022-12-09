package com.example.student.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "run.scripts")
@Component
public class CodeConfig {
    private String cpp;
    private String c;
    private String python;

    public void setCpp(String cpp) {
        this.cpp = cpp;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setPython(String python) {
        this.python = python;
    }

    public String getCpp() {
        return cpp;
    }

    public String getC() {
        return c;
    }

    public String getPython() {
        return python;
    }

}

package com.example.student.util;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-03-13 18:26
 */
public class ProcessResult {
    private int exitCode;

    private String output;

    public ProcessResult(int exitCode, String output) {
        this.exitCode = exitCode;
        this.output = output;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getOutput() {
        return output;
    }
}
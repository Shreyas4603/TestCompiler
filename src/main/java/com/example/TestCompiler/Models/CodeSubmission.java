package com.example.TestCompiler.Models;

import com.example.TestCompiler.Models.TestCase;

import java.util.List;

public class CodeSubmission {
    private String code;
    private String language;
//    private List<TestCase> testCases; // List of test cases

    // Constructors, Getters, and Setters
    public CodeSubmission() {}

    public CodeSubmission(String code, String language) {
        this.code = code;
        this.language = language;
//        this.testCases = testCases;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

//    public List<TestCase> getTestCases() {
//        return testCases;
//    }
//
//    public void setTestCases(List<TestCase> testCases) {
//        this.testCases = testCases;
//    }
}

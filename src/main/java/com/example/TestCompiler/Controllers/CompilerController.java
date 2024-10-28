package com.example.TestCompiler.Controllers;


import com.example.TestCompiler.Models.CodeSubmission;
import com.example.TestCompiler.Services.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/compile")
public class CompilerController {


    @Autowired
    private RunService runService;




    @PostMapping
    public ResponseEntity<String> compileCode(@RequestBody CodeSubmission submission) {
        String result="";
            System.out.println("Data : "+submission.getCode()+"----"+submission.getLanguage());
        try {
            result = runService.compileAndRun(submission);
            System.out.println("res : "+result);
        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}

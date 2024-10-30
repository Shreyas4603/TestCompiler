package com.example.TestCompiler.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ServerController {

    @GetMapping("/")
    private  String serverRun(){
        return "Hi ! Test Compiler server is running";
    }
}

package edu.wj.sport.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("test")
    public List<String> test(){
        return Arrays.asList("1","2","3");
    }
}

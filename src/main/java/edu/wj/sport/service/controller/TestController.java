package edu.wj.sport.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class TestController {

    @GetMapping("test")
    public ResponseEntity<Object> test(){
        return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList("1","2","3"));
    }
}

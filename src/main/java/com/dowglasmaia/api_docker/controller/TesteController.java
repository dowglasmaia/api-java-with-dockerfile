package com.dowglasmaia.api_docker.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testes")
public class TesteController {


    @GetMapping
    public ResponseEntity<String>getTest(){
        return ResponseEntity.status(HttpStatus.OK).body("Api Ok - com Dockerfile");
    }
}

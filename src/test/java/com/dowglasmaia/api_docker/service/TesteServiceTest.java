package com.dowglasmaia.api_docker.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TesteServiceTest {


    @Autowired
    private TesteService testeService;

    @Test
    public void teste_shouldReturnApiOk() {

        String result = testeService.teste();

        Assertions.assertEquals("Api Ok - com Dockerfile", result);
    }

}

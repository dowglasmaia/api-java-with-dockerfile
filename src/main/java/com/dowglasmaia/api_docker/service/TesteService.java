package com.dowglasmaia.api_docker.service;

import org.springframework.stereotype.Service;

@Service
public class TesteService {

    public String teste(){
        return "Api Ok - com Dockerfile";
    }


    private void teste2(){
        System.out.println("teste2");
    }
}

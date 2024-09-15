package com.dowglasmaia.api_docker.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(value = "test")
@WebMvcTest(TesteController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TesteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOkStatusAndMessage() throws Exception{
        mockMvc.perform(get("/api/testes"))
              .andExpect(status().isOk())
              .andExpect(content().string("Api Ok - com Dockerfile"));
    }

}


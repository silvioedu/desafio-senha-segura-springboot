package br.com.senhasegura.validator.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senhasegura.validator.api.dto.PasswordInput;
import br.com.senhasegura.validator.domain.service.PasswordValidatorService;

@WebMvcTest(PasswordController.class)
public class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordValidatorService passwordValidatorService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Mockito.reset(passwordValidatorService);
    }

    @Test
    public void testValidPassword() throws Exception {
        PasswordInput input = new PasswordInput("Valid1@password");
        when(passwordValidatorService.validate(any(PasswordInput.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/v1/validate-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testInvalidPassword() throws Exception {
        PasswordInput input = new PasswordInput("short");
        when(passwordValidatorService.validate(any(PasswordInput.class)))
                .thenReturn(List.of("A senha deve possuir pelo menos 08 caracteres."));

        mockMvc.perform(post("/v1/validate-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPasswordWithMultipleErrors() throws Exception {
        PasswordInput input = new PasswordInput("short");
        when(passwordValidatorService.validate(any(PasswordInput.class)))
                .thenReturn(List.of(
                        "A senha deve possuir pelo menos 08 caracteres.",
                        "A senha deve possuir pelo menos uma letra maiúscula.",
                        "A senha deve possuir pelo menos um caractere especial (e.g, !@#$%)."));

        mockMvc.perform(post("/v1/validate-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }
}

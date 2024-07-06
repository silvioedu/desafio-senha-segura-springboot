package br.com.senhasegura.validator.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.senhasegura.validator.api.dto.PasswordInput;

public class PasswordValidatorServiceTest {

    private PasswordValidatorService passwordValidatorService;

    @BeforeEach
    public void setUp() {
        passwordValidatorService = new PasswordValidatorService();
    }

    @Test
    public void testValidPassword() {
        PasswordInput input = new PasswordInput("Valid1@password");
        List<String> errors = passwordValidatorService.validate(input);
        assertEquals(0, errors.size());
    }

    @Test
    public void testPasswordTooShort() {
        PasswordInput input = new PasswordInput("Short1!");
        List<String> errors = passwordValidatorService.validate(input);
        assertEquals(1, errors.size());
        assertEquals("A senha deve possuir pelo menos 08 caracteres.", errors.get(0));
    }

    @Test
    public void testPasswordMissingUpperCase() {
        PasswordInput input = new PasswordInput("nouppercase1!");
        List<String> errors = passwordValidatorService.validate(input);
        assertEquals(1, errors.size());
        assertEquals("A senha deve possuir pelo menos uma letra maiúscula.", errors.get(0));
    }

    @Test
    public void testPasswordMissingLowerCase() {
        PasswordInput input = new PasswordInput("NOLOWERCASE1!");
        List<String> errors = passwordValidatorService.validate(input);
        assertEquals(1, errors.size());
        assertEquals("A senha deve possuir pelo menos uma letra minúscula.", errors.get(0));
    }

    @Test
    public void testPasswordMissingSpecialChar() {
        PasswordInput input = new PasswordInput("NoSpecialChar1");
        List<String> errors = passwordValidatorService.validate(input);
        assertEquals(1, errors.size());
        assertEquals("A senha deve possuir pelo menos um caractere especial (e.g, !@#$%).", errors.get(0));
    }

    @Test
    public void testPasswordMultipleErrors() {
        PasswordInput input = new PasswordInput("short");
        List<String> errors = passwordValidatorService.validate(input);
        assertEquals(3, errors.size());
        assertEquals("A senha deve possuir pelo menos 08 caracteres.", errors.get(0));
        assertEquals("A senha deve possuir pelo menos uma letra maiúscula.", errors.get(1));
        assertEquals("A senha deve possuir pelo menos um caractere especial (e.g, !@#$%).", errors.get(2));
    }
}

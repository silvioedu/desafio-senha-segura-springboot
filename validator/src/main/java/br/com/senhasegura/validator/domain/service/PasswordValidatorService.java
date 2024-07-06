package br.com.senhasegura.validator.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.senhasegura.validator.api.dto.PasswordInput;

@Service
public class PasswordValidatorService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SPECIAL_CHAR_REGEX = ".*\\W.*";
    private static final String UPPERCASE_CHAR_REGEX = ".*[A-Z].*";
    private static final String LOWERCASE_CHAR_REGEX = ".*[a-z].*";

    public List<String> validate(PasswordInput input) {
        var errors = new ArrayList<String>();

        validateCondition(input.password().length() >= MIN_PASSWORD_LENGTH, 
                          "A senha deve possuir pelo menos 08 caracteres.", errors);

        validateCondition(input.password().matches(UPPERCASE_CHAR_REGEX), 
                          "A senha deve possuir pelo menos uma letra maiúscula.", errors);

        validateCondition(input.password().matches(LOWERCASE_CHAR_REGEX), 
                          "A senha deve possuir pelo menos uma letra minúscula.", errors);

        validateCondition(input.password().matches(SPECIAL_CHAR_REGEX), 
                          "A senha deve possuir pelo menos um caractere especial (e.g, !@#$%).", errors);

        return errors;
    }

    private void validateCondition(boolean condition, String errorMessage, List<String> errors) {
        if (!condition) {
            errors.add(errorMessage);
        }
    }
    
}

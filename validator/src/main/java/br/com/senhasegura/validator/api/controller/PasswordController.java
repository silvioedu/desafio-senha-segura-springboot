package br.com.senhasegura.validator.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senhasegura.validator.api.dto.ErrorsOutput;
import br.com.senhasegura.validator.api.dto.PasswordInput;
import br.com.senhasegura.validator.domain.service.PasswordValidatorService;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(path = "/v1/validate-password")
public class PasswordController {

    private final PasswordValidatorService service;

    public PasswordController(PasswordValidatorService passwordValidatorService) {
        this.service = passwordValidatorService;
    }

    @PostMapping    
    public ResponseEntity<ErrorsOutput> validate(@RequestBody PasswordInput input) {
        
        var errors = service.validate(input);

        if (errors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body(new ErrorsOutput(errors));
    }
}

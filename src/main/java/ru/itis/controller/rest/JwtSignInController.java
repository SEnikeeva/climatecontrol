package ru.itis.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.service.SignInService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JwtSignInController {
    @Autowired
    private SignInService signInService;


    @PreAuthorize("permitAll()")
    @PostMapping("/api/login")
    public ResponseEntity<?> confirmLogin(@Valid @RequestBody SignInDto signInDto) {
        System.out.println("signindto "+signInDto);
        TokenDto token = signInService.signIn(signInDto);
        System.out.println("token"+token);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Wrong data");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

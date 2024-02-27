package com.UpperFasster.Magazine.authorization.controllers;

import com.UpperFasster.Magazine.authorization.DTO.UserCredentialsByEmailDTO;
import com.UpperFasster.Magazine.authorization.services.UserAuthService;
import com.UpperFasster.Magazine.config.ResponseMessage;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserAuthService userAuthService;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @PostMapping("/register/byEmail")
    public ResponseEntity<ResponseMessage> registerNewUserByEmail(
            @Valid @RequestBody UserCredentialsByEmailDTO userCredentialsByEmailDTO
    ) {
        return userAuthService.registerNewUserByEmail(userCredentialsByEmailDTO);
    }

    @PostMapping("/login/byEmail")
    public ResponseEntity<?> loginUserByEmail(
            @Valid @RequestBody UserCredentialsByEmailDTO userCredentialsByEmailDTO
    ) {
        return userAuthService.loginUserByEmail(userCredentialsByEmailDTO);
    }

    @GetMapping("/validate/{token}")
    public boolean testEndpoint(@PathVariable String token) {
        return userAuthService.validateToken(token);
    }

}

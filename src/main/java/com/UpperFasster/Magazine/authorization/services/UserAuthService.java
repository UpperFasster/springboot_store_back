package com.UpperFasster.Magazine.authorization.services;

import com.UpperFasster.Magazine.authorization.DTO.AuthenticationJWTResponseDTO;
import com.UpperFasster.Magazine.authorization.DTO.UserCredentialsByEmailDTO;
import com.UpperFasster.Magazine.authorization.models.User;
import com.UpperFasster.Magazine.authorization.repositories.UserRepository;
import com.UpperFasster.Magazine.config.ResponseMessage;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public ResponseEntity<ResponseMessage> registerNewUserByEmail(UserCredentialsByEmailDTO userCredentialsByEmailDTO) {
        User user = User.builder()
                .email(userCredentialsByEmailDTO.getEmail())
                .password(BCrypt.hashpw(userCredentialsByEmailDTO.getPassword(), BCrypt.gensalt()))
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            new ResponseMessage("User with email: '" + user.getEmail() + "' already exists!")
                    );
        }

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ResponseMessage("User with email: '" + user.getEmail() + "' registered successfully!")
            );
    }

    public ResponseEntity<?> loginUserByEmail(UserCredentialsByEmailDTO userCredentialsByEmailDTO) {

        Optional<User> userResult = userRepository.findByEmail(userCredentialsByEmailDTO.getEmail());

        if (userResult.isPresent()) {
            User user = userResult.get();
            if (BCrypt.checkpw(userCredentialsByEmailDTO.getPassword(), user.getPassword())) {
                UUID rTkn = UUID.randomUUID();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(
                                AuthenticationJWTResponseDTO.builder()
                                        .access(
                                            jwtService.createAccessToken(
                                            UUID.randomUUID(),
                                            user.getId(),
                                            rTkn)
                                        )
                                        .refresh(
                                                jwtService.createRefreshToken(
                                                rTkn,
                                                user.getId())
                                        )
                                        .user_id(user.getId())
                                        .build()
                        );
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ResponseMessage("Email or password is not valid.")
                );
    }

    public boolean validateToken(String jwt) {
        return jwtService.validateToken(jwt);
    }
}

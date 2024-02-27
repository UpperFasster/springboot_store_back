package com.UpperFasster.Magazine.authorization.services;

import com.UpperFasster.Magazine.authorization.models.User;
import com.UpperFasster.Magazine.authorization.models.UserSession;
import com.UpperFasster.Magazine.authorization.repositories.UserRepository;
import com.UpperFasster.Magazine.authorization.repositories.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserSessionServices {
    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;

    @Autowired
    public UserSessionServices(UserRepository userRepository, UserSessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public void createUserAndSession(UUID user_id, UUID refresh_token_id) {

        Optional<User> user = userRepository.findById(user_id);

        if (user.isPresent()) {
            UserSession session = new UserSession();
            // Set other properties for UserSession if needed

        }
    }
}

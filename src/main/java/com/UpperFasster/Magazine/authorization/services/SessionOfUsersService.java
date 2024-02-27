package com.UpperFasster.Magazine.authorization.services;

import com.UpperFasster.Magazine.authorization.models.UserSession;
import com.UpperFasster.Magazine.authorization.repositories.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SessionOfUsersService {
    @Autowired
    private UserSessionRepository sessionRepository;

    public void addNewSession(UserSession userSession) {
        sessionRepository.save(userSession);
    }

    public void delSession(UUID session_id) {
        sessionRepository.deleteById(session_id);
    }

    public Optional<UserSession> getSession(UUID session_id) {
        return sessionRepository.findById(session_id);
    }
}

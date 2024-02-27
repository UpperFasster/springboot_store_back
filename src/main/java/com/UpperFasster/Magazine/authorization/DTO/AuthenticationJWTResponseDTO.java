package com.UpperFasster.Magazine.authorization.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationJWTResponseDTO {
    private String access;
    private String refresh;
    // todo delete that property in future
    private UUID user_id;
}

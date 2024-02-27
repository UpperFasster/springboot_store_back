package com.UpperFasster.Magazine.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseErrorMessage {
    private String status;
    private String error;
}

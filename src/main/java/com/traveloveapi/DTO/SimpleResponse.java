package com.traveloveapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SimpleResponse {
    private String message;
    private int status_code;

    public SimpleResponse(String message) {
        this.message = message;
        status_code = 200;
    }
}
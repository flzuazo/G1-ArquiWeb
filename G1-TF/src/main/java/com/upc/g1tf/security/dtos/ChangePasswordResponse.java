package com.upc.g1tf.security.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ChangePasswordResponse {
    private String message;

    public ChangePasswordResponse(String message) { this.message = message; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

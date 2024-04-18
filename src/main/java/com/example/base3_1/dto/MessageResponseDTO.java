package com.example.base3_1.dto;

import lombok.Data;

@Data
public class MessageResponseDTO {
    private String message;
    private int status;
    private Object data;
    public MessageResponseDTO() {
    }

    public MessageResponseDTO(String message) {
        this.message = message;
    }

    public MessageResponseDTO(String token, Object user) {
        this.message = token;
        this.data = user;
    }
}


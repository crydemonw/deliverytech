package com.deliverytech.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * Classe para representar a resposta de erro
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private Map<String, String> details;

    // Construtor da classe
    public ErrorResponse(int status, String error, String message, String path, Map<String, String> details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }

    // Construtor sem detalhes, alternativo para casos onde não há detalhes adicionais
    public ErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, path, null);
    }

    // Getters para os campos serializáveis
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }   

    public String getError() {
        return error;
    }   

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }   

    public Map<String, String> getDetails() {
        return details;
    }


}

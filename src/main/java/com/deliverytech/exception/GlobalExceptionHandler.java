package com.deliverytech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.HashMap;

/*
* Classe para tratamento global de exceções
* Centraliza a lógica de tratamento de erros e formatação das respostas de erro
*/
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /*
    * Captura erros de validação de campos e formata a resposta com detalhes dos erros
    * Retorna status 400 Bad Request com uma lista de campos inválidos e suas mensagens de erro
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            details.put(error.getField(), error.getDefaultMessage())
        );
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação",
            "Campos inválidos na requisição",
            request.getDescription(false).replace("uri=", ""),
            details
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /*
    * Captura exceções de entidade não encontrada e formata a resposta com detalhes do erro
    * Retorna status 404 Not Found com uma mensagem de erro específica
    */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Recurso não encontrado",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /*
    * Captura exceções de conflito e formata a resposta com detalhes do erro
    * Retorna status 409 Conflict com uma mensagem de erro específica
    */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Conflito",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    /*
    * Captura exceções genéricas para evitar vazamento de detalhes sensíveis e garantir uma resposta consistente
    * Retorna status 500 Internal Server Error com uma mensagem genérica de erro
    */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor",
            "Ocorreu um erro inesperado",
            request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.deliverytech.exception;
/*
* Exceção personalizada para erros de negócio
*/
public class BusinessException extends RuntimeException {
    /**
     * Construtor da exceção
     * @param message Mensagem de erro
     */    
    public BusinessException(String message) {
        super(message);
    }
    
}

package com.deliverytech.exception;
/*
 * Exceção personalizada para conflitos
 */
public class ConflictException extends BusinessException {
    
    /* Construtor da exceção
     * @param message Mensagem de erro
     */
    public ConflictException(String message) {
        super(message);
    }
}

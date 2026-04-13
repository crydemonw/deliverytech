package com.deliverytech.exception;

/*
 * Exceção personalizada para entidades não encontradas
 */

public class EntityNotFoundException extends BusinessException {
    /**
     * Construtor da exceção
     * @param entityName Nome da entidade
     * @param id ID da entidade
     */
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s com ID %d não encontrado", entityName, id));
    }
}

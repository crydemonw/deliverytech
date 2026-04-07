package com.deliverytech.service;

import com.deliverytech.model.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProdutoService {
    Produto cadastrar(Produto produto);
    Optional<Produto> buscarPorId(Long id);
    Page<Produto> buscarPorRestaurante(Long restauranteId, Pageable pageable);
    Produto atualizar(Long id, Produto produtoAtualizado);
    void alterarDisponibilidade(Long id, boolean disponivel);
}

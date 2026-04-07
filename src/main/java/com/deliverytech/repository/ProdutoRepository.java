package com.deliverytech.repository;

import com.deliverytech.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findByRestauranteId(Long restauranteId, Pageable pageable);
    Page<Produto> findByDisponivelTrue(Pageable pageable);
    Page<Produto> findByCategoria(String categoria, Pageable pageable);
    Page<Produto> findByCategoriaContainingIgnoreCase(String categoria, Pageable pageable);
}

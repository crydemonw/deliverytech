package com.deliverytech.repository;

import com.deliverytech.model.Pedido;
import com.deliverytech.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);
    Page<Pedido> findByRestauranteId(Long restauranteId, Pageable pageable);
    Page<Pedido> findByStatus(StatusPedido status, Pageable pageable);
    Page<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}

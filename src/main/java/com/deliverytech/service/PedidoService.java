package com.deliverytech.service;

import com.deliverytech.model.Pedido;
import com.deliverytech.model.StatusPedido;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService {
    Pedido criar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);
    Page<Pedido> listarPorCliente(Long clienteId, Pageable pageable);
    Page<Pedido> listarPorRestaurante(Long restauranteId, Pageable pageable);
    Pedido atualizarStatus(Long id, StatusPedido status);
    void cancelar(Long id);
}

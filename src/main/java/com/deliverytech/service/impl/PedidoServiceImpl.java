package com.deliverytech.service.impl;

import com.deliverytech.model.Pedido;
import com.deliverytech.model.StatusPedido;
import com.deliverytech.repository.PedidoRepository;
import com.deliverytech.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Override
    public Pedido criar(Pedido pedido) {
        pedido.setStatus(StatusPedido.CRIADO);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Page<Pedido> listarPorCliente(Long clienteId, Pageable pageable) {
        return pedidoRepository.findByClienteId(clienteId, pageable);
    }

    @Override
    public Page<Pedido> listarPorRestaurante(Long restauranteId, Pageable pageable) {
        return pedidoRepository.findByRestauranteId(restauranteId, pageable);
    }

    @Override
    public Pedido atualizarStatus(Long id, StatusPedido status) {
        return pedidoRepository.findById(id)
            .map(p -> {
                p.setStatus(status);
                return pedidoRepository.save(p);
            }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Override
    public void cancelar(Long id) {
        pedidoRepository.findById(id).ifPresent(p -> {
            p.setStatus(StatusPedido.CANCELADO);
            pedidoRepository.save(p);
        });
    }
}

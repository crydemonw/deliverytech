package com.deliverytech.controller;

import com.deliverytech.dto.request.RestauranteRequest;
import com.deliverytech.dto.response.RestauranteResponse;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<RestauranteResponse> cadastrar(@Valid @RequestBody RestauranteRequest request) {
        Restaurante restaurante = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .ativo(true)
                .build();
        Restaurante salvo = restauranteService.cadastrar(restaurante);
        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(),
                salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()));
    }

    @GetMapping
    public Page<RestauranteResponse> listarTodos(Pageable pageable) {
        Page<Restaurante> restaurantesPaginados = restauranteService.listarTodos(pageable);
        return restaurantesPaginados.map(r -> new RestauranteResponse(
                r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(),
                r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()));                 
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(r -> new RestauranteResponse(r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(), r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public Page<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria, Pageable pageable) {
        Page<Restaurante> restaurantesPaginados = restauranteService.buscarPorCategoria(categoria, pageable);
        return restaurantesPaginados.map(r -> new RestauranteResponse(
                r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(),
                r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()));        
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteRequest request) {
        Restaurante atualizado = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .build();
        Restaurante salvo = restauranteService.atualizar(id, atualizado);
        return ResponseEntity.ok(new RestauranteResponse(salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(), salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()));
    }
}

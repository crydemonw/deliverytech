package com.deliverytech.controller;

import com.deliverytech.dto.request.RestauranteRequest;
import com.deliverytech.dto.response.RestauranteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
@Tag(name = "Restaurantes", description = "Endpoints para gerenciamento de restaurantes da DeliveryTech")
public class RestauranteController {

    private final RestauranteService restauranteService;

    @PostMapping
    @Operation(summary = "Cadastrar restaurante", description = "Endpoint para cadastrar um novo restaurante na DeliveryTech")
    @ApiResponse(responseCode = "201", description = "Restaurante cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
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

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(new RestauranteResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getTelefone(),
                salvo.getTaxaEntrega(), salvo.getTempoEntregaMinutos(), salvo.getAtivo()));
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes", description = "Endpoint para listar restaurantes ativos de forma paginada")
    @ApiResponse(responseCode = "200", description = "Restaurantes listados com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public Page<RestauranteResponse> listarTodos(Pageable pageable) {
        Page<Restaurante> restaurantesPaginados = restauranteService.listarTodos(pageable);
        return restaurantesPaginados.map(r -> new RestauranteResponse(
                r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(),
                r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()));                 
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Endpoint para buscar um restaurante ativo pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarPorId(id)
                .map(r -> new RestauranteResponse(r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(), r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar restaurantes por categoria", description = "Endpoint para buscar restaurantes ativos por categoria de forma paginada")
    @ApiResponse(responseCode = "200", description = "Restaurantes encontrados com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public Page<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria, Pageable pageable) {
        Page<Restaurante> restaurantesPaginados = restauranteService.buscarPorCategoria(categoria, pageable);
        return restaurantesPaginados.map(r -> new RestauranteResponse(
                r.getId(), r.getNome(), r.getCategoria(), r.getTelefone(),
                r.getTaxaEntrega(), r.getTempoEntregaMinutos(), r.getAtivo()));        
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante", description = "Endpoint para atualizar os dados de um restaurante ativo pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
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

package com.deliverytech.controller;

import com.deliverytech.dto.request.ProdutoRequest;
import com.deliverytech.dto.response.ProdutoResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.ProdutoService;
import com.deliverytech.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest request) {
        Restaurante restaurante = restauranteService.buscarPorId(request.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Produto produto = Produto.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .disponivel(true)
                .restaurante(restaurante)
                .build();

        Produto salvo = produtoService.cadastrar(produto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(new ProdutoResponse(
                salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getDescricao(), salvo.getPreco(), salvo.getDisponivel()));
    }

    @GetMapping("/restaurante/{restauranteId}")
    public Page<ProdutoResponse> listarPorRestaurante(@PathVariable Long restauranteId, Pageable pageable) {
        // Verifica se o restaurante existe antes de buscar os produtos
        if(restauranteService.buscarPorId(restauranteId).isEmpty()) {
            throw new EntityNotFoundException("Restaurante", restauranteId);
        }

        Page<Produto> produtosPaginados = produtoService.buscarPorRestaurante(restauranteId, pageable);
        return produtosPaginados.map(p -> new ProdutoResponse(
                p.getId(), p.getNome(), p.getCategoria(), p.getDescricao(), p.getPreco(), p.getDisponivel()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        Produto atualizado = Produto.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .build();
        Produto salvo = produtoService.atualizar(id, atualizado);
        return ResponseEntity.ok(new ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getCategoria(), salvo.getDescricao(), salvo.getPreco(), salvo.getDisponivel()));
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Void> alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        produtoService.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.noContent().build();
    }
}

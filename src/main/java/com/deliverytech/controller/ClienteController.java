package com.deliverytech.controller;

import com.deliverytech.dto.request.ClienteRequest;
import com.deliverytech.dto.response.ClienteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor

@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes da DeliveryTech")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Endpoint para cadastrar um novo cliente na DeliveryTech")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")    
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        logger.info("Cadastro de cliente iniciado: {}", request.getEmail());

        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .ativo(true)
                .build();

        Cliente salvo = clienteService.cadastrar(cliente);

        logger.debug("Cliente salvo com ID {}", salvo.getId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Endpoint para listar clientes ativos de forma paginada")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public Page<ClienteResponse> listar(Pageable pageable) {
        logger.info("Listando todos os clientes ativos de forma paginada");
        Page<Cliente> clientesPaginados = clienteService.listarAtivos(pageable);
        return clientesPaginados.map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()));
    }

    @GetMapping("/clientes") // Mapeia a URL http://localhost:8080/clientes
    @Operation(summary = "Listar clientes (endpoint simplificado)", description = "Endpoint para listar clientes ativos sem paginação")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public List<ClienteResponse> listarClientesNoEndpointSimples() {
        logger.info("Acessando o endpoint simplificado /clientes");

        return clienteService.listarAtivos(Pageable.unpaged()).getContent().stream()
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Endpoint para buscar um cliente ativo pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);
        return clienteService.buscarPorId(id)
                .map(c -> new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getAtivo()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Endpoint para atualizar os dados de um cliente ativo pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        logger.info("Atualizando cliente ID: {}", id);

        Cliente atualizado = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();

        Cliente salvo = clienteService.atualizar(id, atualizado);

        return ResponseEntity.ok(new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getAtivo()));
    }
        
    @PatchMapping("/{id}/status")
    @Operation(summary = "Ativar/Desativar cliente", description = "Endpoint para ativar ou desativar um cliente pelo seu ID")
    @ApiResponse(responseCode = "204", description = "Status do cliente alterado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    @Operation(summary = "Verificar status da API", description = "Endpoint para verificar se a API está online e obter informações de diagnóstico")
    @ApiResponse(responseCode = "200", description = "API está online") 
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }
}

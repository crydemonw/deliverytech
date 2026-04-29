package com.deliverytech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Tag(name = "Health", description = "Endpoints para verificação de saúde da API da DeliveryTech")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Verificar saúde da API", description = "Endpoint para verificar se a API da DeliveryTech está funcionando corretamente")
    @ApiResponse(responseCode = "200", description = "API está saudável")    
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "javaVersion", System.getProperty("java.version")
        );
    }

    @GetMapping("/info")
    @Operation(summary = "Informações da API", description = "Endpoint para obter informações sobre a API da DeliveryTech, como versão e desenvolvedor")
    @ApiResponse(responseCode = "200", description = "Informações da API retornadas com sucesso")
    public AppInfo info() {
        return new AppInfo(
            "Delivery Tech API",
            "1.0.0",
            "Felipe Martinez", // Coloquei seu nome aqui, pode alterar!
            "JDK 21",
            "Spring Boot 3.2.x"
        );
    }

    // Record para demonstrar recurso do Java 14+ (disponível no JDK 21)
    public record AppInfo(
        String application,
        String version,
        String developer,
        String javaVersion,
        String framework
    ) {}
}
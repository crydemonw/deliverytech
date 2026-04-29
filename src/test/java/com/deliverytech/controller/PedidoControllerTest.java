package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_CLIENTE")
public class PedidoControllerTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Test
    void deveCriarPedidoComSucesso() throws Exception {
        String json = """
                {
                    "clienteId": 1,
                    "restauranteId": 1,
                    "enderecoEntrega": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "bairro": "Centro",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01001-000"
                    },
                    "itens": [
                        {
                            "produtoId": 1,
                            "quantidade": 2
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.restauranteId").value(1))
                .andExpect(jsonPath("$.enderecoEntrega.rua").value("Rua das Flores"))
                .andExpect(jsonPath("$.itens[0].produtoId").value(1))
                .andExpect(jsonPath("$.itens[0].quantidade").value(2));
    }

    @Test
    void naoDeveCriarPedidoComClienteInexistente() throws Exception {
        String json = """
                {
                    "clienteId": 999,
                    "restauranteId": 1,
                    "enderecoEntrega": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "bairro": "Centro",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01001-000"
                    },
                    "itens": [
                        {
                            "produtoId": 1,
                            "quantidade": 2
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void naoDeveCriarPedidoComRestauranteInexistente() throws Exception {   
        String json = """
                {
                    "clienteId": 1,
                    "restauranteId": 999,
                    "itens": [
                        {
                            "produtoId": 1,
                            "quantidade": 2
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void naoDeveCriarPedidoComProdutoInexistente() throws Exception {
        String json = """
                {
                    "clienteId": 1,
                    "restauranteId": 1,
                    "enderecoEntrega": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "bairro": "Centro",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01001-000"
                    },
                    "itens": [
                        {
                            "produtoId": 999,
                            "quantidade": 2
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void naoDeveCriarPedidoComQuantidadeNegativa() throws Exception {
        String json = """
                {
                    "clienteId": 1,
                    "restauranteId": 1,
                    "enderecoEntrega": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "bairro": "Centro",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01001-000"
                    },
                    "itens": [
                        {
                            "produtoId": 1,
                            "quantidade": -1
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarPedidoSemEnderecoEntrega() throws Exception {
        String json = """
                {
                    "clienteId": 1,
                    "restauranteId": 1,
                    "itens": [
                        {
                            "produtoId": 1,
                            "quantidade": 2
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarPedidoComItensVazios() throws Exception {
        String json = """
                {
                    "clienteId": 1,
                    "restauranteId": 1,
                    "enderecoEntrega": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "bairro": "Centro",
                        "cidade": "São Paulo",
                        "estado": "SP",
                        "cep": "01001-000"
                    },
                    "itens": []
                }
                """;

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
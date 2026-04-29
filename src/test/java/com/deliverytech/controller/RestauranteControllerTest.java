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
@WithMockUser(authorities = "ROLE_ADMIN")
public class RestauranteControllerTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Test
    void deveCriarRestauranteComSucesso() throws Exception {
        String json = """
                {
                    "nome": "Restaurante Teste",
                    "categoria": "Italiana",
                    "telefone": "11987654321",
                    "taxaEntrega": 10.50,
                    "tempoEntregaMinutos": 30
                }
                """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nome").value("Restaurante Teste"))
                .andExpect(jsonPath("$.categoria").value("Italiana"))
                .andExpect(jsonPath("$.telefone").value("11987654321"))
                .andExpect(jsonPath("$.taxaEntrega").value(10.50))
                .andExpect(jsonPath("$.tempoEntregaMinutos").value(30))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    void naoDeveCriarRestauranteComNomeEmBranco() throws Exception {
        String json = """
                {
                    "nome": "",
                    "categoria": "Mexicana",
                    "telefone": "11987654321",
                    "taxaEntrega": 5.00,
                    "tempoEntregaMinutos": 25
                }
                """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComCategoriaEmBranco() throws Exception {
        String json = """
                {
                    "nome": "Restaurante Teste",
                    "categoria": "",
                    "telefone": "11987654321",
                    "taxaEntrega": 5.00,
                    "tempoEntregaMinutos": 25
                }
                """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTaxaEntregaNegativa() throws Exception {
        String json = """
                {
                    "nome": "Restaurante Teste",
                    "categoria": "Brasileira",
                    "telefone": "11987654321",
                    "taxaEntrega": -1.00,
                    "tempoEntregaMinutos": 25
                }
                """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarRestauranteComTempoEntregaInvalido() throws Exception {
        String json = """
                {
                    "nome": "Restaurante Teste",
                    "categoria": "Japonesa",
                    "telefone": "11987654321",
                    "taxaEntrega": 10.00,
                    "tempoEntregaMinutos": 5
                }
                """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}

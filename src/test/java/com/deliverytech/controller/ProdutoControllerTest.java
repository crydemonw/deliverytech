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
public class ProdutoControllerTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Test
    void deveCriarProdutoComSucesso() throws Exception {
        String json = """
                {
                    "nome": "Pizza Margherita",
                    "descricao": "Pizza com molho de tomate e queijo",
                    "categoria": "Italiana",
                    "preco": 25.99,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.descricao").value("Pizza com molho de tomate e queijo"))
                .andExpect(jsonPath("$.categoria").value("Italiana"))
                .andExpect(jsonPath("$.preco").value(25.99))
                .andExpect(jsonPath("$.restauranteId").value(1));
    }

    @Test
    void naoDeveCriarProdutoComNomeVazio() throws Exception { 
        String json = """
                {
                    "nome": "",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto sem nome",
                    "preco": 10.00,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
                
    }

    @Test
    void naoDeveCriarProdutoComNomeMenorQueDoisCaracteres() throws Exception {
        String json = """
                {
                    "nome": "A",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto com nome curto",
                    "preco": 10.00,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarProdutoComNomeMaiorQueCemCaracteres() throws Exception {
        String nomeLongo = "A".repeat(101); // Gerando um nome com 101 caracteres
        String json = """
                {
                    "nome": "%s",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto com nome longo",
                    "preco": 10.00,
                    "restauranteId": 1
                }
                """.formatted(nomeLongo);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarProdutoComCategoriaVazia() throws Exception {
        String json = """
                {
                    "nome": "Produto sem categoria",
                    "categoria": "",
                    "descricao": "Produto inválido",
                    "preco": 10.00,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());                
    }

    @Test
    void naoDeveCriarProdutoComDescricaoVazia() throws Exception {
        String json = """
                {
                    "nome": "Produto sem descrição",
                    "categoria": "Categoria Teste",
                    "descricao": "",
                    "preco": 10.00,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());                
    }

    @Test
    void naoDeveCriarProdutoComDescricaoAcimaDe500Caracteres() throws Exception {
        String descricaoLonga = "A".repeat(501); // Gerando uma descrição com 501 caracteres
        String json = """
                {
                    "nome": "Produto com descrição longa",
                    "categoria": "Categoria Teste",
                    "descricao": "%s",
                    "preco": 10.00,
                    "restauranteId": 1
                }
                """.formatted(descricaoLonga);

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());                
    }

    @Test
    void naoDeveCriarProdutoComPrecoZero() throws Exception {
        String json = """
                {
                    "nome": "Produto com preço zero",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto inválido",
                    "preco": 0.00,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarProdutoComPrecoAcimaDoLimite() throws Exception {
        String json = """
                {
                    "nome": "Produto com preço alto",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto inválido",
                    "preco": 10000.01,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarProdutoSemRestauranteId() throws Exception {
        String json = """
                {
                    "nome": "Produto sem restauranteId",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto inválido",
                    "preco": 10.00
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarProdutoComPrecoNegativo() throws Exception { 
        String json = """
                {
                    "nome": "Produto com preço negativo",
                    "categoria": "Categoria Teste",
                    "descricao": "Produto inválido",
                    "preco": -5.00,
                    "restauranteId": 1
                }
                """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());        
    }
   
}
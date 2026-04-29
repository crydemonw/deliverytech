package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_CLIENTE")
public class ClienteControllerTest {
    
    @Autowired 
    MockMvc mockMvc;
    
    @Test
    void deveCriarClienteComSucesso() throws Exception {
        // Tentando criar um cliente válido
        // O JSON deve conter um nome e um email válidos
        // O endpoint deve retornar status 201 Created
        String json = "{\"nome\":\"William\",\"email\":\"william@teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isCreated());
    }

    @Test
    void naoDeveCriarClienteComNomeEmBranco() throws Exception {
        // Tentando criar um cliente com nome em branco
        // O JSON deve conter um nome vazio e um email válido
        // O endpoint deve retornar status 400 Bad Request por conta da anotação @NotBlank no campo nome
        String json = "{\"nome\":\"\",\"email\":\"emailvalido@teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarClienteComNomeMenorQueDoisCaracteres() throws Exception {
        // Tentando criar um cliente com nome menor que dois caracteres
        // O JSON deve conter um nome com menos de dois caracteres e um email válido
        // O endpoint deve retornar status 400 Bad Request por conta da anotação @Size no campo nome
        String json = "{\"nome\":\"A\",\"email\":\"emailvalido@teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarClienteComNomeMaiorQueCemCaracteres() throws Exception {
        // Tentando criar um cliente com nome maior que cem caracteres
        // O JSON deve conter um nome com mais de cem caracteres e um email válido
        // O endpoint deve retornar status 400 Bad Request por conta da anotação @Size no campo nome
        String json = "{\"nome\":\"A\",\"email\":\"emailvalido@teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarClienteComEmailEmBranco() throws Exception {
        // Tentando criar um cliente com email em branco
        // O JSON deve conter um nome válido e um email vazio
        // O endpoint deve retornar status 400 Bad Request por conta da anotação @NotBlank no campo email
        String json = "{\"nome\":\"Nome Válido\",\"email\":\"\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void naoDeveCriarClienteComEmailInvalido() throws Exception {
        // Tentando criar um cliente com email inválido
        // O JSON deve conter um nome válido e um email sem formato correto 
        // O endpoint deve retornar status 400 Bad Request por conta da anotação @Email no campo email
        String json = "{\"nome\":\"Nome Válido\",\"email\":\"invalido-teste.com\"}";
        
        mockMvc.perform(post("/api/clientes")
            .contentType("application/json")
            .content(json))
            .andExpect(status().isBadRequest());
    }
}

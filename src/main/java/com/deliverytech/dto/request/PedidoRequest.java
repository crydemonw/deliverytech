package com.deliverytech.dto.request;

import com.deliverytech.model.Endereco;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restauranteId;

    @NotNull(message = "O endereço de entrega é obrigatório")
    private Endereco enderecoEntrega;

    @NotEmpty(message = "O Pedido deve conter pelo menos um item")  
    @Valid
    private List<ItemPedidoRequest> itens;
}

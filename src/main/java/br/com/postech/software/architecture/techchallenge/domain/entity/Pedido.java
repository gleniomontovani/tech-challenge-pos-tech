package br.com.postech.software.architecture.techchallenge.domain.entity;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

	private Long numeroPedido;
    private Cliente cliente;
    private String dataPedido;
    private Integer statusPedido;
    @NotEmpty
    private List<PedidoProduto> produtos;
}

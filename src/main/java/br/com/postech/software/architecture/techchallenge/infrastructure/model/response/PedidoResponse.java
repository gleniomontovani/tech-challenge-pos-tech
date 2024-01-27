package br.com.postech.software.architecture.techchallenge.infrastructure.model.response;

import java.util.List;

import br.com.postech.software.architecture.techchallenge.domain.entity.Cliente;
import br.com.postech.software.architecture.techchallenge.domain.entity.PedidoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

	private Long numeroPedido;
    private Cliente cliente;
    private String dataPedido;
    private Integer statusPedido;
    private List<PedidoProduto> produtos;
}

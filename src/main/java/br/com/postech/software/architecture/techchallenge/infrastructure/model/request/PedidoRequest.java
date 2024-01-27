package br.com.postech.software.architecture.techchallenge.infrastructure.model.request;

import java.util.List;

import br.com.postech.software.architecture.techchallenge.domain.entity.Cliente;
import br.com.postech.software.architecture.techchallenge.domain.entity.PedidoProduto;

public record PedidoRequest(Long numeroPedido, Cliente cliente, String dataPedido, Integer statusPedido,
		List<PedidoProduto> produtos) {

}

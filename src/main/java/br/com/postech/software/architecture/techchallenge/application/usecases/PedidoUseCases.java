package br.com.postech.software.architecture.techchallenge.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.domain.entity.Pedido;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPedidoEnum;
import br.com.postech.software.architecture.techchallenge.application.gateways.PedidoGateway;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoUseCases {

	private final PedidoGateway pedidoGateway;

	public List<Pedido> findTodosPedidos()throws BusinessException{
		return pedidoGateway.findTodosPedidos();
	}

	public Pedido findById(Long numeroPedido) throws BusinessException{//
		return pedidoGateway.findById(numeroPedido);
	}

	public Pedido fazerPedidoFake(Pedido pedido) throws BusinessException {
		return pedidoGateway.fazerPedidoFake(pedido);
	}
	
	public Pedido atualizarStatusPedido(Long numeroPedido, StatusPedidoEnum statusPedido) {
		return pedidoGateway.atualizarStatusPedido(numeroPedido, statusPedido);
	}
}

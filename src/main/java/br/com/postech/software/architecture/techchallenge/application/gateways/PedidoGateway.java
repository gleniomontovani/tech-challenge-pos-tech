package br.com.postech.software.architecture.techchallenge.application.gateways;

import java.util.List;

import br.com.postech.software.architecture.techchallenge.domain.entity.Pedido;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPedidoEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;

public interface PedidoGateway {

	List<Pedido> findTodosPedidos();
	
	Pedido findById(Long numeroPedido) throws BusinessException;
	
	Pedido fazerPedidoFake(Pedido pedido) throws BusinessException, NotFoundException;
	
	Pedido atualizarStatusPedido(Long numeroPedido, StatusPedidoEnum statusPedido) throws BusinessException, NotFoundException;
}

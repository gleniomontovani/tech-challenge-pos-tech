package br.com.postech.software.architecture.techchallenge.application.gateways;

import java.util.List;

import br.com.postech.software.architecture.techchallenge.domain.entity.HistoricoPagamento;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;

public interface PagamentoGateway {

	Pagamento consultarStatusPagamentoPorPedido(Long numeroPedido);
	
	List<Pagamento> listarPagamentosPendentes();
	
	List<HistoricoPagamento> listarHistoricoPagamentosPorPedido(Long numeroPedido);
}

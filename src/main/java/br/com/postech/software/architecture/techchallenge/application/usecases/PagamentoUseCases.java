package br.com.postech.software.architecture.techchallenge.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.PagamentoGateway;
import br.com.postech.software.architecture.techchallenge.domain.entity.HistoricoPagamento;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoUseCases {

	private final PagamentoGateway pagamentoGateway;
	
	public Pagamento consultarStatusPagamentoPorPedido(Long numeroPedido) {
		return pagamentoGateway.consultarStatusPagamentoPorPedido(numeroPedido);
	}
	
	public List<HistoricoPagamento> listarHistoricoPagamentosPorPedido(Long numeroPedido){
		return pagamentoGateway.listarHistoricoPagamentosPorPedido(numeroPedido);
	}
}

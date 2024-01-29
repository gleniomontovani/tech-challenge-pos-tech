package br.com.postech.software.architecture.techchallenge.application.gateways;

import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;

public interface WebhookGateway {
	
	public Pagamento atualizaPagamento(Pagamento pagamento) throws Exception;
}

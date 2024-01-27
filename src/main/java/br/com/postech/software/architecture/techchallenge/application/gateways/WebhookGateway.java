package br.com.postech.software.architecture.techchallenge.application.gateways;

import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;

public interface WebhookGateway {
	
	public Pagamento atualizaPagamento(Pagamento pagamento) throws NotFoundException;
}

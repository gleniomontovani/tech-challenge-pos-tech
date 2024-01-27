package br.com.postech.software.architecture.techchallenge.application.usecases;

import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.WebhookGateway;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebhookUseCases {
	
	private final WebhookGateway webhookGateway;

	public Pagamento atualizaPagamento(Pagamento pagamento) {
		return webhookGateway.atualizaPagamento(pagamento);
	}
}

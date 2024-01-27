package br.com.postech.software.architecture.techchallenge.infrastructure.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.software.architecture.techchallenge.application.usecases.WebhookUseCases;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.request.PagamentoRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final WebhookUseCases webhookUseCases;
	
	@PostMapping
	public ResponseEntity<String> registerWebhook(@RequestBody PagamentoRequest pagamentoRequest) {
		var pagamento = MAPPER.map(pagamentoRequest, Pagamento.class);

		webhookUseCases.atualizaPagamento(pagamento);
		return ResponseEntity.ok("Pagamento feito com sucesso!");
	}
}

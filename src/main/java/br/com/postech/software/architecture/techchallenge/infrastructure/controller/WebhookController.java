package br.com.postech.software.architecture.techchallenge.infrastructure.controller;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/webhooks")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final WebhookUseCases webhookUseCases;
	
	@PostMapping
	public ResponseEntity<String> registerWebhook(@RequestBody PagamentoRequest pagamentoRequest) {
		var pagamento = MAPPER.map(pagamentoRequest, Pagamento.class);		
		try {
			webhookUseCases.atualizaPagamento(pagamento);			
		}catch (Exception e) {
	        // Verifica se a exceção é uma DataIntegrityViolationException
	        if (e instanceof DataIntegrityViolationException) {
	            // Lidar com a exceção DataIntegrityViolationException
	        	log.error("Já existe este registro.");	            
	        } else {
	            // Lidar com outras exceções, se necessário
	        	log.error("Pagamento não encontrado!");
	        }
		}
		return ResponseEntity.ok("Pagamento processado com sucesso!");
	}
}

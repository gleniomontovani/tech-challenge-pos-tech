package br.com.postech.software.architecture.techchallenge.infrastructure.repository;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.postech.software.architecture.techchallenge.configuration.ControllerMappingConfig;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebhookExecutionRepository {

	private final RestTemplate restTemplate;	
	
	public void executeWebhook(Pagamento pagamento) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final var uri = UriComponentsBuilder
				.newInstance()
				.scheme(ControllerMappingConfig.PROTOCOLO_API_SERVER)
				.host(ControllerMappingConfig.URL_API_LOCAL_HOST)
				.port(ControllerMappingConfig.PORT_API_SERVER)
				.path(ControllerMappingConfig.PATH_WEBHOOK_API)
				.build()
				.toUri();
		
		HttpEntity<Pagamento> request = new HttpEntity<>(pagamento, headers);

		restTemplate.postForObject(uri, request, String.class);	
	}
}

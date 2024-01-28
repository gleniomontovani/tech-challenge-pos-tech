package br.com.postech.software.architecture.techchallenge.infrastructure.scheduler;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.PagamentoGateway;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;
import br.com.postech.software.architecture.techchallenge.domain.util.NumberUtils;
import br.com.postech.software.architecture.techchallenge.infrastructure.repository.WebhookExecutionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoScheduler {
	private final PagamentoGateway pagamentoGateway;
	private final WebhookExecutionRepository webhookExecutionRepository;
	
	@Scheduled(fixedRate = 30000) // Agendado para executar a cada 30 segundos
    @Transactional
	public void verificarPagamentos() {
		//Buscar a lista de pagamentos pendentes 
		List<Pagamento> pagamentosPendentes = pagamentoGateway.listarPagamentosPendentes();
		
//		//fazer uma interação realizar a aprovação de cada pagamento.
		for (Pagamento pagamento : pagamentosPendentes) {
			//Verifica se o pagamento sera aprovado ou recusado.
			//Para isso considera que os pagamento com numero pares seram aprovado e impares seram recusados.
			StatusPagamentoEnum statusPagamento = NumberUtils.isEven(pagamento.getNumeroPagamento().intValue()) 
					? StatusPagamentoEnum.APROVADO 
					: StatusPagamentoEnum.RECUSADO;
			
			pagamento.setStatusPagamento(statusPagamento.getValue());
			webhookExecutionRepository.executeWebhook(pagamento);
        }
	}
}

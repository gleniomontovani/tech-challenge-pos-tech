package br.com.postech.software.architecture.techchallenge.infrastructure.repository;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.WebhookGateway;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.converts.StatusPagamentoParaInteiroConverter;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;
import br.com.postech.software.architecture.techchallenge.domain.util.Constantes;
import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa.HistoricoPagamentoJpaRepository;
import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa.PagamentoJpaRepository;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.HistoricoPagamentoEntity;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.PagamentoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WebhookRepository implements WebhookGateway {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
	
	private final PagamentoJpaRepository pagamentoJpaRepository;
	private final HistoricoPagamentoJpaRepository historicoPagamentoJpaRepository;

	@Override
	public Pagamento atualizaPagamento(Pagamento pagamento) throws NotFoundException {
		Integer tentativas = historicoPagamentoJpaRepository.findByPagamentoId(pagamento.getNumeroPagamento()).stream()
				.map(HistoricoPagamentoEntity::getNumeroTentativas).max(Integer::compare).orElse(0);
		
		//Obtem os valores para caso foi a terceira tentativa de pagamento,
		//Caso contrario atribui valores padroes.
		StatusPagamentoEnum statusPagamento = ((tentativas < 2) ? StatusPagamentoEnum.PENDENTE
				: StatusPagamentoEnum.get(pagamento.getStatusPagamento()));
		String descricaoHistorico = ((tentativas < 2) ? Constantes.FAIL_TRY_PAYMENT : Constantes.SUCESS_MAKE_PAYMENT);
		LocalDateTime dataPagamento = ((tentativas < 2) ? null : LocalDateTime.now());
		
		//Faz a atualizacao do pagamento
		PagamentoEntity pagamentoEntity = pagamentoJpaRepository.findById(pagamento.getNumeroPagamento())
				.map(pagEntity -> {
					pagEntity.setDataPagamento(dataPagamento);
					pagEntity.setStatusPagamento(statusPagamento);
					pagEntity.getHistoricoPagamento()
							.add(HistoricoPagamentoEntity.adicionaHistorico(descricaoHistorico, pagEntity, tentativas));
					
					return pagamentoJpaRepository.save(pagEntity);
				})
				.orElseThrow(() -> new NotFoundException("Pagamento não encontrado!"));
		
		MAPPER.typeMap(PagamentoEntity.class, Pagamento.class)
		  	.addMappings(mapperA -> mapperA.using(new StatusPagamentoParaInteiroConverter())
					.map(PagamentoEntity::getStatusPagamento, Pagamento::setStatusPagamento))
			.addMappings(mapperB -> 
				  mapperB.map(src -> src.getPedido().getId(), Pagamento::setNumeroPedido))
			.addMappings(mapperC -> {
				  mapperC.map(src -> src.getId(), Pagamento::setNumeroPagamento);
		});
		
		return MAPPER.map(pagamentoEntity, Pagamento.class);
	}
}

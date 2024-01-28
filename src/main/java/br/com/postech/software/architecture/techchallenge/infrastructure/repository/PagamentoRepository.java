package br.com.postech.software.architecture.techchallenge.infrastructure.repository;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.PagamentoGateway;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.converts.StatusPagamentoParaInteiroConverter;
import br.com.postech.software.architecture.techchallenge.domain.entity.HistoricoPagamento;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;
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
public class PagamentoRepository implements PagamentoGateway {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
	
	private final PagamentoJpaRepository pagamentoJpaRepository;
	private final HistoricoPagamentoJpaRepository historicoPagamentoJpaRepository;

	@Override
	public Pagamento consultarStatusPagamentoPorPedido(Long numeroPedido) {
		PagamentoEntity pagamentoEntity = pagamentoJpaRepository.findByPedidoId(numeroPedido)
				.orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
		
		return MAPPER.map(pagamentoEntity, Pagamento.class);
	}

	@Override
	public List<Pagamento> listarPagamentosPendentes() {
		List<PagamentoEntity> pagamentosPendentes = pagamentoJpaRepository
				.findByStatusPagamentoIn(Arrays.asList(StatusPagamentoEnum.PENDENTE));
		
		MAPPER.typeMap(PagamentoEntity.class, Pagamento.class)
		  	.addMappings(mapperA -> mapperA.using(new StatusPagamentoParaInteiroConverter())
					.map(PagamentoEntity::getStatusPagamento, Pagamento::setStatusPagamento))
			.addMappings(mapperB -> 
				  mapperB.map(src -> src.getPedido().getId(), Pagamento::setNumeroPedido))
			.addMappings(mapperC -> {
				  mapperC.map(src -> src.getId(), Pagamento::setNumeroPagamento);
		});
		
		return MAPPER.map(pagamentosPendentes, new TypeToken<List<Pagamento>>() {
		}.getType());
	}

	@Override
	public List<HistoricoPagamento> listarHistoricoPagamentosPorPedido(Long numeroPedido) {
		List<HistoricoPagamentoEntity> historicoPagamentosEntity = historicoPagamentoJpaRepository.findByPagamentoId(numeroPedido);
		
		MAPPER.typeMap(HistoricoPagamentoEntity.class, HistoricoPagamento.class)
		  	.addMappings(mapperA -> 
		  		  mapperA.map(src ->src.getDataPagamento(), HistoricoPagamento::setDataPagamento))
			.addMappings(mapperB -> 
				  mapperB.map(src -> src.getPagamento().getPedido().getId(), HistoricoPagamento::setNumeroPedido))
			.addMappings(mapperC -> 
			  	  mapperC.map(src -> src.getPagamento().getValor(), HistoricoPagamento::setValor))
			.addMappings(mapperD -> {
				  mapperD.map(src -> src.getPagamento().getId(), HistoricoPagamento::setNumeroPagamento);
		});
		
		return MAPPER.map(historicoPagamentosEntity, new TypeToken<List<HistoricoPagamento>>() {
		}.getType());
	}

}

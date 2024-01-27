package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.postech.software.architecture.techchallenge.infrastructure.model.HistoricoPagamentoEntity;

public interface HistoricoPagamentoJpaRepository extends JpaRepository<HistoricoPagamentoEntity, Long>{
	
	List<HistoricoPagamentoEntity> findByPagamentoId(Long numeroPagamento);
}
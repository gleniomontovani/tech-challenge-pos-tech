package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.PagamentoEntity;

@Repository
public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, Long>{

	Optional<PagamentoEntity> findByPedidoId(Long numeroPedido);
	
	List<PagamentoEntity> findByStatusPagamentoIn(List<StatusPagamentoEnum> statusPagamentos);
}

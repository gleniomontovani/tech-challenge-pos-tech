package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPedidoEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.PersistenceException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.PedidoEntity;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, Long> {

	List<PedidoEntity> findByStatusPedidoNotInOrderByStatusPedidoDescDataPedidoDesc(List<StatusPedidoEnum> statusPedidos) throws PersistenceException;
}

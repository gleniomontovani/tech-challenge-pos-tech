package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa;

import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ClienteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {

    List<ClienteEntity> findByStatus(Boolean c);

    Optional<ClienteEntity> findByIdAndStatus(Long id, Boolean status) throws NotFoundException;

    Optional<ClienteEntity> findById(Long id) throws NotFoundException;

    Optional<ClienteEntity> findByCpfOrNomeOrEmail(String cpf, String nome, String email) throws NotFoundException;
}

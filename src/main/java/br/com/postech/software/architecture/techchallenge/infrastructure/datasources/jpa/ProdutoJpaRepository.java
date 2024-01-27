package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa;

import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ProdutoEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoJpaRepository extends JpaRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findByCategoria(CategoriaEnum categoria);
    
    Optional<ProdutoEntity> findById(Integer id) throws NotFoundException;

}

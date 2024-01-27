package br.com.postech.software.architecture.techchallenge.application.gateways;

import br.com.postech.software.architecture.techchallenge.domain.entity.Produto;
import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ProdutoEntity;

import java.util.List;

public interface ProdutoGateway {

    List<Produto> findAll(CategoriaEnum categoria);

    Produto findById(Long id) throws NotFoundException;

    ProdutoEntity findProdutoById(Long id) throws NotFoundException;

    Produto save(Produto Produto) throws BusinessException;

    Produto atualizar(Integer id, Produto Produto) throws NotFoundException;
    
    void deleteById(Long id);

}

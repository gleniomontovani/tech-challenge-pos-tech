package br.com.postech.software.architecture.techchallenge.application.usecases;

import br.com.postech.software.architecture.techchallenge.application.gateways.ProdutoGateway;
import br.com.postech.software.architecture.techchallenge.domain.entity.Produto;
import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ProdutoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoUseCases {
    private final ProdutoGateway produtoGateway;
    
    public List<Produto> findAll(CategoriaEnum categoria) {
        return produtoGateway.findAll(categoria);
    }
    
    public Produto findById(Long id) {
        return produtoGateway.findById(id);
    }
    
    public ProdutoEntity findProdutoById(Long id) {
        return produtoGateway.findProdutoById(id);
    }
    
    public Produto save(Produto produto) {
        return produtoGateway.save(produto);
    }
    
    public Produto atualizar(Integer id, Produto produto) {
        return produtoGateway.atualizar(id, produto);
    }
    
    public void deleteById(Long id) {
        produtoGateway.deleteById(id);
    }
}

package br.com.postech.software.architecture.techchallenge.infrastructure.model.request;

import java.math.BigDecimal;
import java.util.List;

import br.com.postech.software.architecture.techchallenge.domain.entity.ProdutoImagem;
import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;

public record ProdutoRequest(String nome, CategoriaEnum categoria, BigDecimal valor, String descricao, List<ProdutoImagem> imagens) {

}

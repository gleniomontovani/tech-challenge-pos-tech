package br.com.postech.software.architecture.techchallenge.infrastructure.model.response;

import java.math.BigDecimal;
import java.util.List;

import br.com.postech.software.architecture.techchallenge.domain.entity.ProdutoImagem;
import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

	private Long id;
    private String nome;
    private CategoriaEnum categoria;
    private BigDecimal valor;
    private String descricao;
    private List<ProdutoImagem> imagens;
}

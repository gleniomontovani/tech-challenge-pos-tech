package br.com.postech.software.architecture.techchallenge.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProduto {

	private Produto produto;
    @NotNull(message = "A quantidade é obrigatório.")
    private Integer quantidade;
}

package br.com.postech.software.architecture.techchallenge.infrastructure.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;
import br.com.postech.software.architecture.techchallenge.domain.util.Constantes;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PagamentoEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "pedido_id")
	private PedidoEntity pedido;
		
	@Column(name = "data_pagamento", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataPagamento;
	
	@Type(value = br.com.postech.software.architecture.techchallenge.domain.enums.AssociacaoType.class,
            parameters = {@Parameter(name = Constantes.ENUM_CLASS_NAME, value = "StatusPagamentoEnum")})
    @Column(name = "status_pagamento_id")
	private StatusPagamentoEnum statusPagamento;
	
	@Column(name = "valor", nullable = false, precision = 10, scale = 2)
	private BigDecimal valor;
	
	@OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<HistoricoPagamentoEntity> historicoPagamento = new ArrayList<>();
}

package br.com.postech.software.architecture.techchallenge.infrastructure.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.software.architecture.techchallenge.application.usecases.PagamentoUseCases;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.HistoricoPagamento;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pagamento;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.response.HistoricoPagamentoResponse;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.response.PagamentoResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
	
	private final PagamentoUseCases pagamentoUseCases;
	
    @GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PagamentoResponse> consultarPagamentoPorPedido(@PathVariable Long numeroPedido) throws Exception {
    	Pagamento pagamento = pagamentoUseCases.consultarStatusPagamentoPorPedido(numeroPedido);
    	
    	PagamentoResponse response = MAPPER.map(pagamento, PagamentoResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    } 
    
    @GetMapping(path = "/historico/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<HistoricoPagamentoResponse>> consultarHistoricoPagamentoPorPedido(@PathVariable Long numeroPedido) throws Exception {
    	List<HistoricoPagamento> historicoPagamento = pagamentoUseCases.listarHistoricoPagamentosPorPedido(numeroPedido);
    	
		List<HistoricoPagamentoResponse> response = MAPPER.map(historicoPagamento,
				new TypeToken<List<HistoricoPagamentoResponse>>() {
				}.getType());
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

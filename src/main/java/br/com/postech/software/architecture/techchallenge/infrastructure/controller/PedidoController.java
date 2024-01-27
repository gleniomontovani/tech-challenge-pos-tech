package br.com.postech.software.architecture.techchallenge.infrastructure.controller;

import br.com.postech.software.architecture.techchallenge.application.usecases.PedidoUseCases;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pedido;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPedidoEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.response.PedidoResponse;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

    private final PedidoUseCases pedidoUseCases;

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<PedidoResponse>> listarTodosPedidos() throws Exception {
    	List<Pedido> lista = pedidoUseCases.findTodosPedidos();
    	
    	List<PedidoResponse> response = lista.stream()
                .map(pedido -> MAPPER.map(pedido, PedidoResponse.class))
                .collect(Collectors.toList());
    	    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Long numeroPedido) throws Exception {
    	Pedido pedido = pedidoUseCases.findById(numeroPedido);
    	
    	PedidoResponse response = MAPPER.map(pedido, PedidoResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/checkout", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoResponse> fazerCheckoutFake(@RequestBody Pedido pedido) throws Exception {
    	Pedido checkout = pedidoUseCases.fazerPedidoFake(pedido);
    	
    	PedidoResponse response = MAPPER.map(checkout, PedidoResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping(path = "/status", produces = MediaType.APPLICATION_JSON, params = {"numeroPedido", "statusPedido"})
    public ResponseEntity<PedidoResponse> atualizarStatusPedido(@RequestParam Long numeroPedido, @RequestParam StatusPedidoEnum statusPedido) throws Exception {
        Pedido pedido = pedidoUseCases.atualizarStatusPedido(numeroPedido, statusPedido);
        
        PedidoResponse response = MAPPER.map(pedido, PedidoResponse.class);
    	
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

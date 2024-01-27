package br.com.postech.software.architecture.techchallenge.infrastructure.controller;

import br.com.postech.software.architecture.techchallenge.application.usecases.ClienteUseCases;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.Cliente;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.request.ClienteRequest;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.response.ClienteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
    
    private final ClienteUseCases clienteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        List<Cliente> clientesAtivos = clienteService.listarClientesAtivos();
        List<ClienteResponse> response = clientesAtivos.stream()
                .map(cliente -> MAPPER.map(cliente, ClienteResponse.class))
                .collect(Collectors.toList());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "{idCliente}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> buscarCliente(@PathVariable("idCliente") Long idCliente) throws Exception {
    	var cliente = clienteService.findById(idCliente);
    	   	
    	ClienteResponse response = MAPPER.map(cliente, ClienteResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> salvarCliente(@RequestBody @Valid ClienteRequest clienteRequest) throws Exception {
    	var cliente = MAPPER.map(clienteRequest, Cliente.class);
    	cliente.setStatus(Boolean.TRUE);
    	
    	var clienteSalvo = clienteService.save(cliente);
    	
    	ClienteResponse response = MAPPER.map(clienteSalvo, ClienteResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest) {
    	var cliente = MAPPER.map(clienteRequest, Cliente.class);
    	var clienteAtualizado = clienteService.atualizarCliente(id, cliente);
 
        ClienteResponse response = MAPPER.map(clienteAtualizado, ClienteResponse.class);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/desativar/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> desativarCliente(@PathVariable Long id) {
        Cliente clienteDesativado = clienteService.desativarCliente(id);

        ClienteResponse response = MAPPER.map(clienteDesativado, ClienteResponse.class);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }	
}

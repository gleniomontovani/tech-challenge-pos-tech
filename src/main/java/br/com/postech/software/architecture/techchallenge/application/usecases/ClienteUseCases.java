package br.com.postech.software.architecture.techchallenge.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.ClientGateway;
import br.com.postech.software.architecture.techchallenge.domain.entity.Cliente;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteUseCases {
	private final ClientGateway clientGateway;
	
	public List<Cliente> listarClientesAtivos() {
		return clientGateway.listarClientesAtivos();
	}
	
	public Cliente findById(Long id) {
		return clientGateway.findById(id);
	}
	
	public Cliente save(Cliente cliente) {
		return clientGateway.save(cliente);
	}
	
	public Cliente atualizarCliente(Long id, Cliente cliente) {
		return clientGateway.atualizarCliente(id, cliente);
	}
	
	public Cliente desativarCliente(Long id) {
		return clientGateway.desativarCliente(id);
	}
}

package br.com.postech.software.architecture.techchallenge.application.gateways;

import br.com.postech.software.architecture.techchallenge.domain.entity.Cliente;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ClienteEntity;

import java.util.List;

public interface ClientGateway {

    List<Cliente> listarClientesAtivos();

    Cliente findById(Long id) throws NotFoundException;

    Cliente save(Cliente Cliente);

    Cliente atualizarCliente(Long id, Cliente Cliente) throws NotFoundException;

    Cliente desativarCliente(Long id) throws NotFoundException;

    ClienteEntity findByCpfOrNomeOrEmail(String cpf, String nome, String email) throws NotFoundException;

}

package br.com.postech.software.architecture.techchallenge.infrastructure.repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.ClientGateway;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.Cliente;
import br.com.postech.software.architecture.techchallenge.domain.util.CpfCnpjUtil;
import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa.ClienteJpaRepository;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ClienteEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteRepository implements ClientGateway{
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final ClienteJpaRepository clienteJpaRepository;
	
	@Override
	public List<Cliente> listarClientesAtivos() {
      List<ClienteEntity> clientesAtivos = clienteJpaRepository.findByStatus(Boolean.TRUE);
      return clientesAtivos.stream()
              .map(cliente -> MAPPER.map(cliente, Cliente.class))
              .collect(Collectors.toList());
	}

	@Override
	public Cliente findById(Long id) {
      ClienteEntity cliente = clienteJpaRepository.findByIdAndStatus(id, Boolean.TRUE)
    		  .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
          
      return MAPPER.map(cliente, Cliente.class);      
	}

	@Override
	public Cliente save(Cliente cliente) {
      var clienteEntity = MAPPER.map(cliente, ClienteEntity.class);
      
      if(Objects.nonNull(clienteEntity.getCpf())) {
      	cliente.setCpf(CpfCnpjUtil.removeMaskCPFCNPJ(cliente.getCpf()));
      }
      
      clienteEntity = clienteJpaRepository.save(clienteEntity);

      return MAPPER.map(clienteEntity, Cliente.class);
	}

	@Override
	public Cliente atualizarCliente(Long id, Cliente cliente) {
		ClienteEntity clienteEntity = clienteJpaRepository.findById(id).map(clienteMappado -> {
			clienteMappado.setNome(cliente.getNome());
			clienteMappado.setEmail(cliente.getEmail());
			clienteMappado.setCpf(cliente.getCpf());
			return clienteJpaRepository.save(clienteMappado);
		}).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

		return MAPPER.map(clienteEntity, Cliente.class);
	}

	@Override
	public Cliente desativarCliente(Long id) {
		ClienteEntity clienteEntity = clienteJpaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

      clienteEntity.setStatus(Boolean.FALSE);
      clienteEntity = clienteJpaRepository.save(clienteEntity);

      return MAPPER.map(clienteEntity, Cliente.class);
	}

  @Override
  public ClienteEntity findByCpfOrNomeOrEmail(String cpf, String nome, String email) throws NotFoundException {
		return clienteJpaRepository.findByCpfOrNomeOrEmail(cpf, nome, email)
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado!"));
  }

}

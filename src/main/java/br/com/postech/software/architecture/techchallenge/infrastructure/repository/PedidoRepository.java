package br.com.postech.software.architecture.techchallenge.infrastructure.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.ClientGateway;
import br.com.postech.software.architecture.techchallenge.application.gateways.PedidoGateway;
import br.com.postech.software.architecture.techchallenge.application.gateways.ProdutoGateway;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.converts.InteiroParaStatusPedidoConverter;
import br.com.postech.software.architecture.techchallenge.domain.converts.StatusPedidoParaInteiroConverter;
import br.com.postech.software.architecture.techchallenge.domain.entity.Pedido;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;
import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPedidoEnum;
import br.com.postech.software.architecture.techchallenge.domain.util.CpfCnpjUtil;
import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa.PagamentoJpaRepository;
import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa.PedidoJpaRepository;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ClienteEntity;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.PagamentoEntity;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.PedidoEntity;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.PedidoProdutoEntity;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ProdutoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoRepository implements PedidoGateway{
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();
	
	private final PedidoJpaRepository pedidoJpaRepository;
	private final PagamentoJpaRepository pagamentoJpaRepository;
	private final ClientGateway clientService;
	private final ProdutoGateway produtoService;

	@Override
	public List<Pedido> findTodosPedidos() {
		List<PedidoEntity> pedidos = pedidoJpaRepository
				.findByStatusPedidoNotInOrderByStatusPedidoDescDataPedidoDesc(Arrays.asList(StatusPedidoEnum.FINALIZADO));

		MAPPER.typeMap(PedidoEntity.class, Pedido.class)
				.addMappings(mapperA -> mapperA.using(new StatusPedidoParaInteiroConverter())
						.map(PedidoEntity::getStatusPedido, Pedido::setStatusPedido))
				.addMappings(mapper -> {
					mapper.map(src -> src.getId(), Pedido::setNumeroPedido);
				});

		return MAPPER.map(pedidos, new TypeToken<List<Pedido>>() {
		}.getType());
	}

	@Override
	public Pedido findById(Long numeroPedido) throws BusinessException {
		PedidoEntity pedido = pedidoJpaRepository.findById(numeroPedido)
				.orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));

		return MAPPER.map(pedido, Pedido.class);
	}

	@Override
	public Pedido fazerPedidoFake(Pedido pedido) throws BusinessException, NotFoundException {
		//Obtem os dados do pedido
		MAPPER.typeMap(Pedido.class, PedidoEntity.class)
		.addMappings(mapperA -> mapperA
				.using(new InteiroParaStatusPedidoConverter())
					.map(Pedido::getStatusPedido, PedidoEntity::setStatusPedido));

		PedidoEntity pedidoEntity = MAPPER.map(pedido, PedidoEntity.class);
		pedidoEntity.setDataPedido(LocalDateTime.now());
		pedidoEntity.setStatusPedido(StatusPedidoEnum.RECEBIDO);

		valideCliente(pedidoEntity);

		valideProduto(pedidoEntity);

		//Salva o pedido e obtem seu numero
		pedidoEntity = pedidoJpaRepository.save(pedidoEntity);
		
		//Obtem o valor total do pedido
		BigDecimal valorPedido = pedidoEntity.getProdutos()
				.stream()
				.map(PedidoProdutoEntity::total)
				.reduce((x, y) -> x.add(y))
				.get();
		
		//Cria um registro de pagamento no banco como Pendente
		PagamentoEntity pagamentoEntity = new PagamentoEntity(null, pedidoEntity, null,
				StatusPagamentoEnum.PENDENTE, valorPedido, null);
		
		pagamentoJpaRepository.save(pagamentoEntity);		
		
		MAPPER.typeMap(PedidoEntity.class, Pedido.class)
		.addMappings(mapperA -> mapperA
				.using(new StatusPedidoParaInteiroConverter())
					.map(PedidoEntity::getStatusPedido, Pedido::setStatusPedido))
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(), Pedido::setNumeroPedido);
		});

		return MAPPER.map(pedidoEntity, Pedido.class);
	}
	
	@Override
	public Pedido atualizarStatusPedido(Long numeroPedido, StatusPedidoEnum statusPedido)
			throws BusinessException, NotFoundException {
		
		PedidoEntity pedidoEntity = pedidoJpaRepository.findById(numeroPedido)
				.orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
		
		pedidoEntity.setStatusPedido(statusPedido);
		
		//Atualiza o status de um determinado pedido
		pedidoEntity = pedidoJpaRepository.save(pedidoEntity);
		
		MAPPER.typeMap(PedidoEntity.class, Pedido.class)
		.addMappings(mapperA -> mapperA
				.using(new StatusPedidoParaInteiroConverter())
					.map(PedidoEntity::getStatusPedido, Pedido::setStatusPedido))
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(), Pedido::setNumeroPedido);
		});
		
		return MAPPER.map(pedidoEntity, Pedido.class);
	}
	
	private void valideProduto(PedidoEntity pedido) throws BusinessException, NotFoundException {
		// Verifica se o está cadastrado produtos
		Optional.ofNullable(pedido.getProdutos())
				.orElseThrow(() -> new BusinessException("É obrigatório informar algum produto!")).stream()
				.filter(pedidoProduto -> Objects.nonNull(pedidoProduto.getProduto())
						&& Objects.nonNull(pedidoProduto.getProduto().getId()))
				.findAny().orElseThrow(() -> new BusinessException("Produto não cadastrado!"));

		// Atribui atualiza lista de pedido_produto.
		pedido.getProdutos().stream().distinct().forEach(pedidoProduto -> {
			pedidoProduto.setPedido(pedido);
			ProdutoEntity produto = produtoService.findProdutoById(pedidoProduto.getProduto().getId());
			pedidoProduto.setProduto(produto);
		});
	}

	private void valideCliente(PedidoEntity pedido) throws BusinessException {
		// Caso informe dados do cliente, é obrigatorio o cliente existir
		if (Objects.nonNull(pedido.getCliente())) {
			pedido.getCliente().setCpf(CpfCnpjUtil.removeMaskCPFCNPJ(pedido.getCliente().getCpf()));

			ClienteEntity cliente = clientService
					.findByCpfOrNomeOrEmail(pedido.getCliente().getCpf(), pedido.getCliente().getNome(),
							pedido.getCliente().getEmail());

			pedido.setCliente(cliente);
		}
	}
}

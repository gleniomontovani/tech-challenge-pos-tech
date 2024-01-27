package br.com.postech.software.architecture.techchallenge.infrastructure.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import br.com.postech.software.architecture.techchallenge.application.gateways.ProdutoGateway;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.Produto;
import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jpa.ProdutoJpaRepository;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.infrastructure.exception.NotFoundException;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.ProdutoEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoRepository implements ProdutoGateway {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final ProdutoJpaRepository produtoJpaRepository;

	@Override
	public List<Produto> findAll(CategoriaEnum categoria) {
		if (Objects.nonNull(categoria)) {
			return MAPPER.map(produtoJpaRepository.findByCategoria(categoria), new TypeToken<List<Produto>>() {
			}.getType());
		}

		return MAPPER.map(produtoJpaRepository.findAll(), new TypeToken<List<Produto>>() {
		}.getType());
	}

	@Override
	public Produto findById(Long id) throws NotFoundException{
		ProdutoEntity produto = produtoJpaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Produto não encontrado."));

		return MAPPER.map(produto, Produto.class);
	}

	@Override
	public ProdutoEntity findProdutoById(Long id) throws NotFoundException{
		return produtoJpaRepository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado."));
	}

	@Override
	public Produto save(Produto Produto) throws BusinessException{
		var produto = MAPPER.map(Produto, ProdutoEntity.class);

		validateImagesProduto(produto);
		produto = produtoJpaRepository.save(produto);

		return MAPPER.map(produto, Produto.class);
	}

	@Override
	public Produto atualizar(Integer id, Produto produto) throws NotFoundException, BusinessException{
		produtoJpaRepository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
		
		var produtoAtual = MAPPER.map(produto, ProdutoEntity.class);
		produtoAtual.setId(Long.valueOf(id));
		validateImagesProduto(produtoAtual);

		produtoAtual = produtoJpaRepository.save(produtoAtual);

		return MAPPER.map(produtoAtual, Produto.class);
	}

	@Override
	public void deleteById(Long id) {
		produtoJpaRepository.deleteById(id);
	}

	private void validateImagesProduto(ProdutoEntity produto) throws BusinessException{
		Optional.ofNullable(produto.getImagens())
				.orElseThrow(() -> new BusinessException("É obrigatório informar pelo menos uma imgem para o produto!"))
				.stream().filter(img -> Objects.isNull(img.getProduto())).forEach(img -> {
					img.setProduto(produto);
				});
	}

}

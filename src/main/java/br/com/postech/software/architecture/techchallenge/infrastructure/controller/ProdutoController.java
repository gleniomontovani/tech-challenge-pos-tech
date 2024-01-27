package br.com.postech.software.architecture.techchallenge.infrastructure.controller;

import br.com.postech.software.architecture.techchallenge.application.usecases.ProdutoUseCases;
import br.com.postech.software.architecture.techchallenge.configuration.ModelMapperConfiguration;
import br.com.postech.software.architecture.techchallenge.domain.entity.Produto;
import br.com.postech.software.architecture.techchallenge.domain.enums.CategoriaEnum;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.request.ProdutoRequest;
import br.com.postech.software.architecture.techchallenge.infrastructure.model.response.ProdutoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

    private final ProdutoUseCases produtoUseCases;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarProdutos(@RequestParam(required = false) CategoriaEnum categoria) {
    	List<Produto> listaPorCategoria = produtoUseCases.findAll(categoria);
    	
    	List<ProdutoResponse> response = listaPorCategoria.stream()
                .map(produto -> MAPPER.map(produto, ProdutoResponse.class))
                .collect(Collectors.toList()); 
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarProdutoPorId(@PathVariable Long id) {
    	var produto = produtoUseCases.findById(id);
    	
    	var response = MAPPER.map(produto, ProdutoResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody @Valid ProdutoRequest produtoRequest) {
        var produto = MAPPER.map(produtoRequest, Produto.class);
    	
    	var produtoSalvo = produtoUseCases.save(produto);
        
    	var response = MAPPER.map(produtoSalvo, ProdutoResponse.class);
    	
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Integer id, @RequestBody @Valid ProdutoRequest produtoRequest) {
    	var produto = MAPPER.map(produtoRequest, Produto.class);
    	
    	var produtoAtualizado = produtoUseCases.atualizar(id, produto);
    	
    	var response = MAPPER.map(produtoAtualizado, ProdutoResponse.class);
    	
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.produtoUseCases.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

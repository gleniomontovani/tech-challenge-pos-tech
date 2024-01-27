package br.com.postech.software.architecture.techchallenge.domain.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;

public class InteiroParaStatusPagamentoConverter extends AbstractConverter<Integer, StatusPagamentoEnum> {

	@Override
	protected StatusPagamentoEnum convert(Integer source) {
		return StatusPagamentoEnum.get(source);
	}

}

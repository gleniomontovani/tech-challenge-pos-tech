package br.com.postech.software.architecture.techchallenge.domain.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPagamentoEnum;

public class StatusPagamentoParaInteiroConverter extends AbstractConverter<StatusPagamentoEnum , Integer>{

	@Override
	protected Integer convert(StatusPagamentoEnum source) {
		return source == null ? null : source.getValue();
	}

}

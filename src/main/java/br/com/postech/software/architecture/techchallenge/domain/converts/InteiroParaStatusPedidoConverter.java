package br.com.postech.software.architecture.techchallenge.domain.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.software.architecture.techchallenge.domain.enums.StatusPedidoEnum;

public class InteiroParaStatusPedidoConverter extends AbstractConverter<Integer, StatusPedidoEnum> {

	@Override
	protected StatusPedidoEnum convert(Integer source) {
		return StatusPedidoEnum.get(source);
	}

}

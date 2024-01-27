package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jdbc.IClienteJdbcRepository;

@Repository
public class ClienteRepositoryImpl implements IClienteJdbcRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected JdbcTemplate getPersistencia() {
		return jdbcTemplate;
	}	
	
}

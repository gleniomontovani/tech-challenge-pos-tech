package br.com.postech.software.architecture.techchallenge.infrastructure.datasources.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoJdbcRepositoryImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected JdbcTemplate getPersistencia() {
		return jdbcTemplate;
	}
}

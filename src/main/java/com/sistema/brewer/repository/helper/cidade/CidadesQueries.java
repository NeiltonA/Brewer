package com.sistema.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema.brewer.model.Cidade;
import com.sistema.brewer.repository.filter.CidadeFilter;



public interface CidadesQueries {
	
	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable);

	public Cidade buscaPorCidade(Long codigo);
}

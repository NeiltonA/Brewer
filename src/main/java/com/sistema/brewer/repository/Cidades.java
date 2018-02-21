package com.sistema.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.brewer.model.Cidade;
import com.sistema.brewer.model.Estado;
import com.sistema.brewer.repository.helper.cidade.CidadesQueries;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	

	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);



	
}

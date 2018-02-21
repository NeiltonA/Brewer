package com.sistema.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sistema.brewer.model.Cidade;
import com.sistema.brewer.repository.Cidades;
import com.sistema.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.sistema.brewer.service.exception.NomeCidadeJaCadastradoException;

@Service
public class CadastroCidadeService {

	@Autowired
	private Cidades cidades;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Optional<Cidade> estiloOptional = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		if (estiloOptional.isPresent()) {
			throw new NomeCidadeJaCadastradoException("Nome da cidade já cadastrado");
		}
		
		return cidades.saveAndFlush(cidade);
	}

	@Transactional
	public void excluir(Cidade cidade) {
		try {
		cidades.delete(cidade);
		cidades.flush();
	}catch (PersistenceException e) {
		throw new ImpossivelExcluirEntidadeException("Impossivel apagar a cidade. já foi usada em outra venda");
	}

	}
}
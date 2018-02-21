package com.sistema.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sistema.brewer.model.Cliente;
import com.sistema.brewer.repository.Clientes;
import com.sistema.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import com.sistema.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private Clientes clintes;

	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> estiloOptional = clintes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		//verifica se o cpf já existe
		if (estiloOptional.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		
		clintes.save(cliente);

	}


	@Transactional
	public void excluir(Cliente cliente) {
		try {
			clintes.delete(cliente);
			clintes.flush();
	}catch (PersistenceException e) {
		throw new ImpossivelExcluirEntidadeException("Impossivel apagar o cliente. já está sendo usado");
	}
	}

}

package com.sistema.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.brewer.model.Cliente;
import com.sistema.brewer.repository.helper.cliente.ClientesQueries;

@Repository
public interface Clientes  extends JpaRepository<Cliente, Long>, ClientesQueries {

	//'Optional' pode existir ou não (Optional = so apartir do java 8)
	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}

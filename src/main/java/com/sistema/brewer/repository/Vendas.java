package com.sistema.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.brewer.model.Venda;
import com.sistema.brewer.repository.helper.venda.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries{

}

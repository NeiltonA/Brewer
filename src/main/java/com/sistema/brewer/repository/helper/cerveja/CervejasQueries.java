package com.sistema.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema.brewer.dto.CervejaDTO;
import com.sistema.brewer.dto.ValorItensEstoque;
import com.sistema.brewer.model.Cerveja;
import com.sistema.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {
	
	public Page<Cerveja> filtrar(CervejaFilter filro, Pageable pageable);


	public List<CervejaDTO> porSkuOuNome(String skuOuNome);


	public ValorItensEstoque valorItensEstoque();
}

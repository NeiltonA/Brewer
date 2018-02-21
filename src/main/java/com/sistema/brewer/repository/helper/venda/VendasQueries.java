package com.sistema.brewer.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sistema.brewer.dto.VendaMes;
import com.sistema.brewer.dto.VendaOrigem;
import com.sistema.brewer.model.Venda;
import com.sistema.brewer.repository.filter.VendaFilter;



public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter filro, Pageable pageable);

	public Venda buscarComItens(Long codigo);
	
	public BigDecimal valorTotalNoAno();
	
	public BigDecimal valorTotalNoMes();
	
	public BigDecimal valorTicketMedioNoAno();
	
	public List<VendaMes> totalPorMes();

	public List<VendaOrigem> totalPorOrigem();


}

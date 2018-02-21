package com.sistema.brewer.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.sistema.brewer.session.TabelaItensVenda;

public class TabelaItensVendaTest {
	
	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda("1");
	}

	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		
		//BigDecimal.ZERO primeiro parametro valor esperado o segundo "tabelaItensVenda.getValorTotal" o valor atual
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	}

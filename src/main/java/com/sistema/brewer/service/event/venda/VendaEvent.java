package com.sistema.brewer.service.event.venda;

import com.sistema.brewer.model.Venda;

public class VendaEvent  {
	
	private Venda venda;

	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}
	
	

}

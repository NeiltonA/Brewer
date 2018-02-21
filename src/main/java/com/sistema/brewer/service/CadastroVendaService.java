package com.sistema.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.brewer.model.StatusVenda;
import com.sistema.brewer.model.Venda;
import com.sistema.brewer.repository.Vendas;
import com.sistema.brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if (venda.isSalvarProibido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda proibida");
		}
		
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());// se for nova pega a data atual
		}else {
			Venda vendExistente = vendas.findOne(venda.getCodigo());
			venda.setDataCriacao(vendExistente.getDataCriacao());
		}
				
		if (venda.getDataEntrega() !=null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), 
					venda.getHorarioEntrega() !=null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
		
		return vendas.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
		
		publisher.publishEvent(new VendaEvent(venda));
	}

	//"#" representa o elemento venda //principal é o usuario do sistema
	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")// regra quem pode chamar este metodo quem criou a venda
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendExistente = vendas.findOne(venda.getCodigo());
		
		
		vendExistente.setStatus(StatusVenda.CANCELADA);
		vendas.save(vendExistente);
		
	}

	@Transactional
	public void excluir(Venda venda) {
		vendas.delete(venda);
		vendas.flush();

}
}

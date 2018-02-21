package com.sistema.brewer.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;

public class PeriodoRelatorio {


	@NotBlank(message = "A data inicio é obrigatória")
	private LocalDate dataInicio;
	

	@NotBlank(message = "A data fim é obrigatória")
	private LocalDate dataFim;

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	
	
}

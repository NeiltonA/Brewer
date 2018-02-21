package com.sistema.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrosController {

	@GetMapping("/404")
	public String paginaNaoEcontrada() {
		return "404";
	}
	
	@RequestMapping("/500")
	public String errorServidor() {
		return "500";
	}
	
}

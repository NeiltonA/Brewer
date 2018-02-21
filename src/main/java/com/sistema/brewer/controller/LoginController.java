package com.sistema.brewer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		if (user !=null) {
			return "redirect:/cervejas";
			
		}
		return "Login";
	}
	
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	

	@GetMapping("/quant-usuario-logado")
	public String qauntUserLogado() {
		return "quant-usuario-logado";
	}
	
	@GetMapping("/invalida-session")
	public String invalidaSession() {
		return "invalida-session";
	}
}



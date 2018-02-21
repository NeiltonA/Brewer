package com.sistema.brewer.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.brewer.model.Usuario;
import com.sistema.brewer.repository.Usuarios;


@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//se não encontrar o usuario é lançado uma exceção
		Optional<Usuario> usuarioOptional = usuarios.porEmailEAtivo(email);
		
		//se não encontrar o usuario é lançado uma exceção/ expreção "->"lamina para lançar a exceção
		Usuario usuario  = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha incorreto"));

		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	public  Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		//Lista de permissoes do usuario
		List<String> permissoes = usuarios.permissoes(usuario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		return authorities;
	}

}

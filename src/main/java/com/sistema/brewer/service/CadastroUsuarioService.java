package com.sistema.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sistema.brewer.model.Usuario;
import com.sistema.brewer.repository.Usuarios;
import com.sistema.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.sistema.brewer.service.exception.NomeEmailJaCadastradoException;
import com.sistema.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarios.findByEmail(usuario.getEmail());
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NomeEmailJaCadastradoException("E-mail já cadastrado");
		}
		
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para o novo usuário");
		}
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		}else if(StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
			
		}
			usuario.setConfirmacaoSenha(usuario.getSenha());
	
		if (!usuario.isNovo() && usuario.getAtivo() ==null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}
		
		return usuarios.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
	statusUsuario.executar(codigos, usuarios);
		
	}
	@Transactional
	public void excluir(Usuario usuario) {
		try {
		usuarios.delete(usuario);
		usuarios.flush();
	}catch (PersistenceException e) {
		throw new ImpossivelExcluirEntidadeException("Impossivel apagar o Usuário. já  está sendo usado");
	}
	}
	
}
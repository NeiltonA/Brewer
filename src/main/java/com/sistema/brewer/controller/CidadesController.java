package com.sistema.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.brewer.controller.page.PageWrapper;
import com.sistema.brewer.model.Cidade;
import com.sistema.brewer.repository.Cidades;
import com.sistema.brewer.repository.Estados;
import com.sistema.brewer.repository.filter.CidadeFilter;
import com.sistema.brewer.service.CadastroCidadeService;
import com.sistema.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.sistema.brewer.service.exception.NomeCidadeJaCadastradoException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

	@Autowired
	public Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	//'@CacheEvict' invalida o cache da cidade						//se tiver estado em cidade executa o cach 	'@CacheEvict'
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")//'key = chave do cach = 'codigoEstado'
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cidade);
		}
		
		// Salvar no banco de dados...
		try {
		cadastroCidadeService.salvar(cidade);
		} catch (NomeCidadeJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso");
		return new ModelAndView("redirect:/cidades/novo");
	}

	@Cacheable(value = "cidades", key = "#codigoEstado") // configuração para colocar em cach a cidade
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado){
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter,  BindingResult result, @PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
		mv.addObject("estados", estados.findAll());

		
		PageWrapper<Cidade> paginaWrapper = new PageWrapper<>(cidades.filtrar(cidadeFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	//Metodo para deletar a cidades
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo")Cidade cidade){
		try {
		
		cadastroCidadeService.excluir(cidade);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	//Metodo para editar a cidade
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		Cidade cidade = cidades.buscaPorCidade(codigo);
		
		ModelAndView mv = novo(cidade);
		mv.addObject(cidade);
		
		return mv;
	}
	
}
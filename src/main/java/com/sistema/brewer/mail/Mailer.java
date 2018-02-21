package com.sistema.brewer.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sistema.brewer.model.Cerveja;
import com.sistema.brewer.model.ItemVenda;
import com.sistema.brewer.model.Venda;
import com.sistema.brewer.storage.FotoStorage;


@Component
public class Mailer {

	
	
	@Autowired
	private JavaMailSender mailSend;
	
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	//chamadas assíncronas
	@Async
	public void enviar(Venda venda) {

	Context context = new Context(new Locale("pt", "BR"));
	context.setVariable("venda", venda);
	context.setVariable("logo", "logo");
	

	Map<String, String> fotos = new HashMap<>();
	boolean adicioarMockCerveja = false;
	for(ItemVenda item : venda.getItens()) { // pega todos item
		Cerveja cerveja = item.getCerveja(); //pega a cerveja
		if (cerveja.temFoto()) {
			String cid = "foto-" + cerveja.getCodigo();//gera um nome que precisa colocar no contexto para cada uma das fotos
			context.setVariable(cid, cid);
			fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());	
		}else {
			adicioarMockCerveja = true;
			context.setVariable("mockCerveja", "mockCerveja");
		}
		


	}
	
	
	try {
		String email = thymeleaf.process("mail/ResumoVenda", context);
		MimeMessage mimeMessage = mailSend.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setFrom("testesistemaney@gmail.com");
		helper.setTo(venda.getCliente().getEmail());
		helper.setSubject(String.format("Brewer - Venda n° %d", venda.getCodigo()));
		helper.setText(email, true);
		
		helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
		
		if (adicioarMockCerveja) {
			helper.addInline("mockCerveja", new ClassPathResource("static/images/cerveja-mock.png"));
			
		}
		
		for(String cid : fotos.keySet()) { //percorre os cid que colocou no map
			String[] fotoContentType = fotos.get(cid).split("\\|");
			String foto = fotoContentType[0];
			String contentType = fotoContentType[1];
			byte[] arrayFoto = fotoStorage.recuperarThumbnail(foto);//recupera o thumbanail com o nome da foto
			helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);;
		}
		
		
		
		mailSend.send(mimeMessage);
	} catch (Exception e) {
		e.getMessage();
	}
	
	}
}

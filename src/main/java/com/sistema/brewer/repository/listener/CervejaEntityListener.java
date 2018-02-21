package com.sistema.brewer.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.stereotype.Component;

import com.sistema.brewer.model.Cerveja;
import com.sistema.brewer.storage.FotoStorage;

@Component
public class CervejaEntityListener {

//	@Autowired
//	private FotoStorage fotoStorage;

	@PostLoad
	public void postLoad(final Cerveja cerveja) {
//		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		cerveja.setUrlFoto(FotoStorage.URL + cerveja.getFotoOuMock());
		cerveja.setUrlThumbnailFoto(FotoStorage.URL + FotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOuMock());
	}

}
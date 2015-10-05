package snet.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import snet.model.entities.Language;

public abstract class AbstractController {

	public static final int PAGINATION_FIRST_PAGE = 1;
	public static final int PAGINATION_TOTAL_PER_PAGE = 10;

	@Autowired
	private HttpSession session;

	/**
	 * Configurações comuns do DataBinder do Spring.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		// não intancia automaticamente objetos null em uma entidade
		binder.setAutoGrowNestedPaths(false);
	}

	/**
	 * Obtém o idioma atual da aplicação, definido no LanguageInterceptor.
	 */
	public Language currLang() {
		return (Language) session.getAttribute("_currLang"); 
	}
}

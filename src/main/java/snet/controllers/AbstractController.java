package snet.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import snet.model.entities.Language;

public abstract class AbstractController {

	@Autowired
	private HttpSession session;

	/**
	 * Obtém o idioma atual da aplicação, definido no LanguageInterceptor.
	 */
	public Language currLang() {
		return (Language) session.getAttribute("_currLang"); 
	}
}

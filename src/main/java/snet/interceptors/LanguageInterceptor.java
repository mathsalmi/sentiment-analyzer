package snet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import snet.model.entities.Language;
import snet.model.services.LanguageService;

/**
 * Define o idioma utilizado
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class LanguageInterceptor implements HandlerInterceptor {

	@Autowired
	private LanguageService langService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);

		if(session.getAttribute("curr_lang") == null) {
			Language lang = langService.findById("en");
			session.setAttribute("curr_lang", lang);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}

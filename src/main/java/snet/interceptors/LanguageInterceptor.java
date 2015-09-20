package snet.interceptors;

import static snet.util.AppConstants.APP_ALL_LANGS;
import static snet.util.AppConstants.APP_CURR_LANG;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import snet.model.entities.Language;
import snet.model.services.LanguageService;

/**
 * Modifica e seleciona idioma.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class LanguageInterceptor implements HandlerInterceptor {

	@Autowired
	private LanguageService langService;

	public static List<Language> cacheLangs;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView == null) {
			return;
		}

		if(cacheLangs == null || cacheLangs.isEmpty()) {
			cacheLangs = langService.listAllActive();
		}

		if(cacheLangs == null || cacheLangs.isEmpty()) {
			throw new Exception("Necessário configurar idiomas ativos para a aplicação!");
		}

		Language currLang = null;
		HttpSession session = request.getSession(true);
		if(session.getAttribute(APP_CURR_LANG) != null) {
			currLang = (Language) session.getAttribute(APP_CURR_LANG); 
		} else {
			currLang = cacheLangs.get(0);
		}

		// save on the session
		session.setAttribute(APP_CURR_LANG, currLang);

		// save on the view
		ModelMap mm = modelAndView.getModelMap();
		mm.addAttribute(APP_CURR_LANG, currLang);
		mm.addAttribute(APP_ALL_LANGS, cacheLangs);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}

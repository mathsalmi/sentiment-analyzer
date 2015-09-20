package snet.interceptors;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import snet.model.entities.Language;
import snet.model.services.LanguageService;
import snet.util.AppConstants;

/**
 * Modifica e seleciona idioma.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class LanguageInterceptor implements HandlerInterceptor {

	@Autowired
	private LanguageService langService;

	public static final Map<String, Language> cacheLangs = new LinkedHashMap<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView == null) {
			return;
		}

		loadCache();

		if(cacheLangs.isEmpty()) {
			throw new Exception("Necessário configurar idiomas ativos para a aplicação!");
		}

		HttpSession session = request.getSession(true);
		Language currLang = (Language) session.getAttribute(AppConstants.APP_CURR_LANG);

		// if changing language
		String langParam = request.getParameter(AppConstants.APP_CURR_LANG);
		if(StringUtils.isNotBlank(langParam)) {
			currLang = cacheLangs.get(langParam);
		}

		// if still no language is selected, select the first added
		if(currLang == null) {
			currLang = cacheLangs.get(cacheLangs.keySet().toArray()[0]);
		}

		// save on the session
		session.setAttribute(AppConstants.APP_CURR_LANG, currLang);

		// save on the view
		ModelMap mm = modelAndView.getModelMap();
		mm.addAttribute(AppConstants.APP_CURR_LANG, currLang);
		mm.addAttribute(AppConstants.APP_ALL_LANGS, cacheLangs.values());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	private void loadCache() {
		if(cacheLangs.isEmpty()) {
			List<Language> langs = langService.listAllActive();
			if(langs != null && langs.size() > 0) {
				for (Language lang : langs) {
					cacheLangs.put(lang.getId(), lang);
				}
			}
		}
	}
}

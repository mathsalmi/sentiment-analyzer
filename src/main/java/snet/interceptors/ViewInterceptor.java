package snet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import snet.annotations.MasterView;
import snet.util.AppConstants;

/**
 * Intercepta as requisições e utiliza os retornos dos métodos dos controladores
 * para incluí-los numa master page.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class ViewInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 * Aponta todos os métodos dos controladores para a view master
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		// serve original content if no model is attached
		if(modelAndView == null) {
			return;
		}

		// checks for the MasterView page
		boolean shouldInject = false;
		String masterViewFolder = null;
		String masterViewName = null;
		try {
			HandlerMethod hm = (HandlerMethod) handler;
			MasterView ann = hm.getMethodAnnotation(MasterView.class); // on the method
			if(ann == null) {
				ann = hm.getBeanType().getAnnotation(MasterView.class); // on the class
			}
			if(ann == null) {
				return;
			}

			shouldInject = ann.enable();

			masterViewFolder = ann.folder();
			if(StringUtils.isBlank(masterViewFolder)) {
				masterViewFolder = AppConstants.APP_MASTER_DEF_VIEW_FOLDER;
			}

			masterViewName = ann.name();
			if(StringUtils.isBlank(masterViewName)) {
				masterViewName = AppConstants.APP_MASTER_DEF_VIEW_NAME;
			}
		} catch(Exception e) { }

		if(!shouldInject) {
			return;
		}

		// ready to go!
		String viewName = modelAndView.getViewName();
		if (viewName.startsWith("redirect:") || viewName.startsWith("forward:")) {
			return;
		}

		modelAndView.setViewName(masterViewFolder + "/" + masterViewName);
		modelAndView.getModelMap().addAttribute(AppConstants.APP_MASTER_VIEW_ATTR, viewName);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}

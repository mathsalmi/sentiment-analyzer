package snet.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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

		String viewName = modelAndView.getViewName();
		if (viewName.startsWith("redirect:") || viewName.startsWith("forward:")) {
			return;
		}

		modelAndView.setViewName("master/index");
		modelAndView.getModelMap().addAttribute("_page", viewName);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}

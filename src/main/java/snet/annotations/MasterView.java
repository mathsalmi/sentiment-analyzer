package snet.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import snet.util.AppConstants;

/**
 * Especifica o tipo de layout a ser utilizado.
 * 
 * Utilizado pelo ViewInterceptor para identificar se deve injetar a View de
 * retorno do método num layout padrão e qual layout setar.
 * 
 * Se o atributo name for vazio, utilizará o conjunto presente em master/index.
 * 
 * Caso o atributo enable seja false, o interceptor não injetará o View de
 * retorno.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface MasterView {
	String folder() default AppConstants.APP_MASTER_DEF_VIEW_FOLDER;

	String name() default AppConstants.APP_MASTER_DEF_VIEW_NAME;

	boolean enable() default true;
}

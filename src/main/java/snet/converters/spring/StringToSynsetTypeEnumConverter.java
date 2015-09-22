package snet.converters.spring;

import org.springframework.core.convert.converter.Converter;

import snet.enums.SynsetTypeEnum;

/**
 * Retorna a opção do enum dado seu código.
 * 
 * Utilizado pelo Spring, por exemplo, ao fazer conversões dos inputs da tela
 * para entidade.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class StringToSynsetTypeEnumConverter implements Converter<String, SynsetTypeEnum> {

	@Override
	public SynsetTypeEnum convert(String source) {
		return SynsetTypeEnum.findByCode(source);
	}
}
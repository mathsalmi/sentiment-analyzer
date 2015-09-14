package teste.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import teste.enums.SynsetTypeEnum;

/**
 * Retorna o código do Enum selecionado antes de salvar o registro no DB
 * (utilizado pelo Hibernate).
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
@Converter
public class SynsetTypeEnumConverter implements AttributeConverter<SynsetTypeEnum, String> {
	@Override
	public String convertToDatabaseColumn(SynsetTypeEnum attribute) {
		return attribute.getCode();
	}

	@Override
	public SynsetTypeEnum convertToEntityAttribute(String dbData) {
		return SynsetTypeEnum.findByCode(dbData);
	}
}
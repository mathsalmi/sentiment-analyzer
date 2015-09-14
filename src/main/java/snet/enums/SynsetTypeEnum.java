package snet.enums;

/**
 * Tipos de synset.
 * 
 * @see http://wordnet.princeton.edu/man/wndb.5WN.html#sect3
 * @author Matheus Salmi <mathsalmi@gmail.com>
 *
 */
public enum SynsetTypeEnum {

	NOUN("n"), VERB("v"), ADJECTIVE("a"), ADVERB("r");

	private final String code;

	SynsetTypeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static SynsetTypeEnum findByCode(String code) {
		for (SynsetTypeEnum item : values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}

		return null;
	}
}

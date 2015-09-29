package snet.enums;

/**
 * Classificações de uma frase.
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public enum PhraseScoreEnum {

	VERY_NEGATIVE(1), NEGATIVE(2), NEUTRAL(3), POSITIVE(4), VERY_POSITIVE(5);

	private final int code;

	PhraseScoreEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static PhraseScoreEnum findByCode(int code) {
		for (PhraseScoreEnum item : values()) {
			if (item.getCode() == code) {
				return item;
			}
		}

		return null;
	}

	// TODO: should replace to use lang packs on layout
	@Override
	public String toString() {
		String out = null;

		switch(this) {
		case VERY_NEGATIVE:
			out = "Muito negativo";
			break;
		case NEGATIVE:
			out = "Negativo";
			break;
		case NEUTRAL:
			out = "Neutro";
			break;
		case POSITIVE:
			out = "Positivo";
			break;
		case VERY_POSITIVE:
			out = "Muito positivo";
			break;
		}

		return out;
	}
}

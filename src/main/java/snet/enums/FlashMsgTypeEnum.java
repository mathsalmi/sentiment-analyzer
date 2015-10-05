package snet.enums;

/**
 * Tipos de mensagens do controlador -> view
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public enum FlashMsgTypeEnum {

	SUCCESS("success"), INFO("info"), WARNING("warning"), ERROR("danger");

	private final String code;

	FlashMsgTypeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static FlashMsgTypeEnum findByCode(String code) {
		for (FlashMsgTypeEnum item : values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}

		return null;
	}
}

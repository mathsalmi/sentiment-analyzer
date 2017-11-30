package snet.dto;

import snet.enums.FlashMsgTypeEnum;

/**
 * Mensagem do controlador -> view
 * 
 * @author Matheus Salmi <mathsalmi@gmail.com>
 */
public class FlashMsg {
	private String message;
	private FlashMsgTypeEnum type;

	public FlashMsg(String message) {
		this(message, FlashMsgTypeEnum.INFO);
	}

	public FlashMsg(String message, FlashMsgTypeEnum type) {
		setMessage(message);
		setType(type);
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public FlashMsgTypeEnum getType() {
		return type;
	}
	public void setType(FlashMsgTypeEnum type) {
		this.type = type;
	}
}

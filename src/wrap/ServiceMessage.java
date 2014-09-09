package wrap;

import java.io.Serializable;

public class ServiceMessage implements Serializable{
	public int getCode() {
		return code;
	}
	public String getText() {
		return text;
	}
	public int code;
	public String text;
	
	public ServiceMessage() {
		code = -10000;
		text = "default";
	}
	
	public ServiceMessage(int code, String text) {
		this.code = code;
		this.text = text;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setText(String text) {
		this.text = text;
	}
}

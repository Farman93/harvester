package wrap;

public class StringMessage extends ServiceMessage {
	public String data;
	
	public StringMessage() {
		super();
		data = null;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public StringMessage(int code, String msg, String data) {
		super(code, msg);
		this.data = data;
	}
}

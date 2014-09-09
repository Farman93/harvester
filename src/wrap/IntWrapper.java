package wrap;
import java.io.*;
public class IntWrapper implements Serializable{
	public Integer value;

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}

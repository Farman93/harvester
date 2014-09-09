
//Сообщение, содержащее помимо кода и текста сообщения массив с протоколам, используемых для харвестинга

package wrap;

import java.util.ArrayList;

import dbElem.Protocol;

/**
 * @author Kirill Kosolapov
 *
 */
public class ProtocolMessage extends ServiceMessage {
	public Protocol[] protocolArray;
	
	public ProtocolMessage() {
		super();
		protocolArray = null;
	}
	public ProtocolMessage(int code, String text, Protocol[] prt) {
		super(code, text);
		protocolArray = prt;
	}
	
	
	public Protocol[] getProtocolArray() {
		return protocolArray;
	}
	public void setProtocolArray(Protocol[] protocolArray) {
		this.protocolArray = protocolArray;
	}
	
	public boolean getProtocolArrayFromList (ArrayList<Protocol> prt) {
		if ( prt == null )
			return false;
		int size = prt.size();
		this.protocolArray = new Protocol[size];
		for (int i = 0; i < size; ++i) {
			this.protocolArray[i] = prt.get(i);
		}
		return true;
	}
	
}

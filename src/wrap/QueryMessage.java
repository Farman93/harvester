

package wrap;


import java.util.ArrayList;

import dbElem.Query;

public class QueryMessage extends ServiceMessage {
	public Query[] queryArray;
	
	
	public QueryMessage() {
		super();
		queryArray = null;
	}
	public QueryMessage(int code, String text, Query[] qr) {
		super(code, text);
		this.queryArray = qr;
	}
	
	
	public void setQueryArray(Query[] qr) {
		this.queryArray = qr;
	}
	public Query[] getQueryArray() {
		return queryArray;
	}
	public boolean getQueryArrayFromList (ArrayList<Query> qr) {
		if ( qr == null )
			return false;
		int size = qr.size();
		this.queryArray = new Query[size];
		for (int i = 0; i < size; ++i) {
			this.queryArray[i] = qr.get(i);
		}
		return true;
	}
	
}

package dbInteractions;

import dbElem.Query;
import wrap.ServiceMessage;



public class QueryChecking {
	public static ServiceMessage isCorrect(Query qr) {
		String msg = null;
		if (qr == null) {
			return new ServiceMessage(6,msg);
		}
		
		if (qr.endURL == null) {
			return new ServiceMessage(7,msg);
		}
		
		if (qr.startURL == null) {
			return new ServiceMessage(8,msg);
		}
		
		if (qr.protocol_id == null) {
			return new ServiceMessage(9,msg);
		}
		
		if (qr.time == null) {
			return new ServiceMessage(10,msg);
		}
		
		if (qr.reg == null) {
			return new ServiceMessage(11,msg);
		}
		
		try {
			if (!DBCheckProtocol.dbConnect(Integer.parseInt(qr.protocol_id))) { 
				return new ServiceMessage(12,msg);
			}
		} catch (NumberFormatException e) {
			return new ServiceMessage(13,msg);
		}
		
		try {
			int regInt = Integer.parseInt(qr.reg);
			if (regInt < 0) {
				return new ServiceMessage(14,msg);
			}
		} catch (NumberFormatException e) {
			return new ServiceMessage(15,msg);
		}
		
		 if (!SQLDatetime.isValidDatetime(qr.time)) {
			 return new ServiceMessage(16,msg);
		 }
		 return new ServiceMessage(1,msg);
			
	}
}

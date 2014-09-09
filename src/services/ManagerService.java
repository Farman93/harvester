package services;


import java.util.ArrayList;

import stringProcedures.LoginString;
import wrap.IntWrapper;
import wrap.ProtocolMessage;
import wrap.QueryMessage;
import wrap.ScheduleMessage;
import wrap.ServiceMessage;
import wrap.StringMessage;
import xml.ErrorMessagesXMLParser;
import dbElem.Protocol;
import dbElem.Query;
import dbElem.ScheduleElement;
import dbInteractions.*;

 

public class ManagerService {
	
	final static String[] msgArr = ErrorMessagesXMLParser.main("ErrorMessages.xml",ErrorMessagesXMLParser.RU);
	
	public static void main(String[] args) {
		ProtocolMessage msg = getProtocolForId("aleph", "aleph",8);
		System.out.println(msg.getProtocolArray()[0].getXml());
	}
	
	
	
	public static QueryMessage getQueryInfo(String login, String pw, int qid) {
		IntWrapper uid = new IntWrapper();
		Query qr = new Query();
		QueryMessage msg = new QueryMessage(-10000,null,null);
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				qr = DBSelectQueryForId.dbConnect(qid);
				if (qr == null) {
					msg.code = 17;
					msg.text = msgArr[msg.code];
					return msg;
				} else {
					msg.queryArray = new Query[1];
					msg.queryArray[0] = qr;
					msg.code = 1;
					msg.text = msgArr[msg.code];
					return msg;
				}
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
	}
	
	
	
	public static ServiceMessage updateQuery(String login, String pw, Query qr) {//Обновит таймер
		ServiceMessage msg = new ServiceMessage(-10000,null);
		
		IntWrapper uid = new IntWrapper();
		
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				msg = QueryChecking.isCorrect(qr);
				if (msg.code != 1) {
					msg.text = msgArr[msg.code];
					return msg;
				}
				if (qr == null) {
					msg.code = 3;
					msg.text = msgArr[msg.code];
					return msg;
				}
				if (DBSelectQueryForId.dbConnect(Integer.parseInt(qr.id))!=null) {
					if (DBUpdateQuery.dbConnect(Integer.parseInt(qr.id), qr.name,qr.endURL,qr.startURL,qr.protocol_id,qr.time,qr.reg,qr.struct_loc)) {
						msg.code = 1;
						msg.text = msgArr[msg.code];
						return msg;
					} else {
						msg.code = 4;
						msg.text = msgArr[msg.code];
						return msg;
					}
				} else {
					msg.code = 5;
					msg.text = msgArr[msg.code];
					return msg;
				}
				
				
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static StringMessage addQuery(String login, String pw, Query qr) {//Обновит таймер
		
		
		
		
		StringMessage msg = new StringMessage(-10000,null,"-1");
		int query_id = -1;
		IntWrapper uid = new IntWrapper();
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				ServiceMessage sMsg = QueryChecking.isCorrect(qr);
				msg.code = sMsg.code;
				msg.text = sMsg.text;
						
				if (msg.code != 1) {
					msg.text = msgArr[msg.code];
					return msg;
				}
				if (qr == null) {
					msg.code = 3;
					msg.text = msgArr[msg.code];
					return msg;
				}
				if (!DBCheckQueryExistance.dbConnect(qr.endURL,qr.startURL,qr.protocol_id,qr.time,qr.reg,String.valueOf(uid.value),qr.struct_loc)) {
					query_id = DBAddQuery.dbConnect(qr.name,qr.endURL,qr.startURL,qr.protocol_id,qr.time,qr.reg,String.valueOf(uid.value),qr.struct_loc);
					if (query_id > 0) {
						msg.code = 1;
						msg.text = msgArr[msg.code];
						msg.data = String.valueOf(query_id);
						return msg;
					} else {
						msg.code = 4;
						msg.text = msgArr[msg.code];
						return msg;
					}
				} else {
					msg.code = 5;
					msg.text = msgArr[msg.code];
					return msg;
				}
				
				
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static QueryMessage getQueriesForUser(String login, String pw) {
		IntWrapper uid = new IntWrapper();
		ArrayList<Query> arr = new ArrayList<Query>();
		boolean noXMLflag = false;
		if (msgArr == null)
			noXMLflag = true;
		QueryMessage msg = new QueryMessage(-10000,null,null);
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = noXMLflag ? "Providing error message has failed. Ошибка при изъятии сообщения ошибки." : msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				arr = DBSelectQueryForUser.dbConnect(login);
				if (arr == null) {
					msg.code = 17;
					msg.text = noXMLflag ? "Providing error message has failed. Ошибка при изъятии сообщения ошибки." : msgArr[msg.code];
					return msg;
				} else {
					msg.getQueryArrayFromList(arr);
					msg.code = 1;
					msg.text = noXMLflag ? "Providing error message has failed. Ошибка при изъятии сообщения ошибки." : msgArr[msg.code];
					return msg;
				}
			} else {
				msg.code = 2;
				msg.text = noXMLflag ? "Providing error message has failed. Ошибка при изъятии сообщения ошибки." : msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static ScheduleMessage getFailedAttemptsForQuery(String login, String pw, int qid) {
		IntWrapper uid = new IntWrapper();
		ScheduleMessage msg = new ScheduleMessage(-10000,null,null);
		ArrayList<ScheduleElement> arr = new ArrayList<ScheduleElement>();
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				
				arr = DBCheckScheduleForQuery.dbConnect(qid, uid.value);
				if (arr == null) {
					msg.code = 18;
					msg.text = msgArr[msg.code];
					return msg;
				} else {
					msg.getScheduleArrayFromList(arr);
					msg.code = 1;
					msg.text = msgArr[msg.code];
					return msg;
							
				}
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static ServiceMessage register(String login, String pw) {
		ServiceMessage msg = new ServiceMessage(-10000,null);
		if (!LoginString.check(login)) {
			msg.code = 19;
			msg.text = msgArr[msg.code];
			return msg;
		}
		if (!DBSelectUser.dbConnect(login)) {
			DBAddUser.dbConnect(login, pw);
			msg.code = 1;
			msg.text = msgArr[msg.code];
			return msg;
		} else {
			msg.code = 20;
			msg.text = msgArr[msg.code];
			return msg;
		}
	}
	
	public static ServiceMessage deleteQuery(String login, String pw, int qid) {//Обновит таймер
		IntWrapper uid = new IntWrapper();
		ServiceMessage msg = new ServiceMessage(-10000,null);
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				int deleteResult = DBDeleteQuery.dbConnect(qid,uid.value);
				switch (deleteResult) {
					case 1: 
						msg.code = 1;
						msg.text = msgArr[msg.code];
						return msg;
					case -1: 
						msg.code = 21;
						msg.text = msgArr[msg.code];
						return msg;
					case 0:
						msg.code = 22;
						msg.text = msgArr[msg.code];
						return msg;
					default:
						msg.code = 0;
						msg.text = msgArr[msg.code];
						return msg;
				}
				
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}

	public static ServiceMessage activateQuery(String login, String pw, int qid) {
		
		IntWrapper uid = new IntWrapper();
		ServiceMessage msg = new ServiceMessage(-10000,null);
		
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				int deleteResult = DBActivateQuery.dbConnect(qid,uid.value);
				switch (deleteResult) {
					case 1: 
						msg.code = 1;
						msg.text = msgArr[msg.code];
						return msg;
					case -1: 
						msg.code = 23;
						msg.text = msgArr[msg.code];
						return msg;
					case 0:
						msg.code = 22;
						msg.text = msgArr[msg.code];
						return msg;
					default:
						msg.code = 0;
						msg.text = msgArr[msg.code];
						return msg;
				}
				
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static ServiceMessage deactivateQuery(String login, String pw, int qid) {
		
		ServiceMessage msg = new ServiceMessage(-10000,null);
		IntWrapper uid = new IntWrapper();
		
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				int deleteResult = DBDeactivateQuery.dbConnect(qid,uid.value);
				switch (deleteResult) {
					case 1: 
						msg.code = 1;
						msg.text = msgArr[msg.code];
						return msg;
					case -1: 
						msg.code = 24;
						msg.text = msgArr[msg.code];
						return msg;
					case 0:
						msg.code = 22;
						msg.text = msgArr[msg.code];
						return msg;
					default:
						msg.code = 0;
						msg.text = msgArr[msg.code];
						return msg;
				}
				
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static ProtocolMessage getProtocols(String login, String pw) {
		IntWrapper uid = new IntWrapper();
		ProtocolMessage msg = new ProtocolMessage();
		ArrayList<Protocol> arr = new ArrayList<Protocol>();
		
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				
				arr = DBSelectProtocols.dbConnect();
				if (arr == null) {
					msg.code = 25;
					msg.text = msgArr[msg.code];
					return msg;
				} else {
					msg.getProtocolArrayFromList(arr);
					msg.code = 1;
					msg.text = msgArr[msg.code];
					return msg;	
				}
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static StringMessage getProtocolNameForId(String login, String pw, int pid) {
		IntWrapper uid = new IntWrapper();
		StringMessage msg = new StringMessage();
		String name = new String();
		
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				try {
					if (!DBCheckProtocol.dbConnect(pid)) { 
						msg.code = 12;
						msg.text = msgArr[msg.code];
						return msg;
					}
				} catch (NumberFormatException e) {
					msg.code = 13;
					msg.text = msgArr[msg.code];
					return msg;
				}
				
				name = DBSelectProtocolNameForId.dbConnect(pid);
				if (name == null) {
					msg.code = 0;
					msg.text = msgArr[msg.code];
					return msg;
				} else {
					msg.data = name;
					msg.code = 1;
					msg.text = msgArr[msg.code];
					return msg;	
				}
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
	
	public static ProtocolMessage getProtocolForId(String login, String pw, int pid) {
		IntWrapper uid = new IntWrapper();
		ProtocolMessage msg = new ProtocolMessage();
		Protocol protocol = null;
		
		if (!DBSelectUser.dbConnect(login)) {
			msg.code = 2;
			msg.text = msgArr[msg.code];
			return msg;
		} else
			if (DBCheckPass.dbConnect(login, pw, uid)) {
				try {
					if (!DBCheckProtocol.dbConnect(pid)) { 
						msg.code = 12;
						msg.text = msgArr[msg.code];
						return msg;
					}
				} catch (NumberFormatException e) {
					msg.code = 13;
					msg.text = msgArr[msg.code];
					return msg;
				}
				
				protocol = DBSelectProtocolForId.dbConnect(pid);
				if (protocol == null) {
					msg.code = 0;
					msg.text = msgArr[msg.code];
					return msg;
				} else {
					msg.protocolArray = new Protocol[1];
					msg.protocolArray[0] = protocol;
					msg.code = 1;
					msg.text = msgArr[msg.code];
					return msg;	
				}
			} else {
				msg.code = 2;
				msg.text = msgArr[msg.code];
				return msg;
			}
		
	}
}
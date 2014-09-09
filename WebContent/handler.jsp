<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="services.ManagerService"%>
<%@page import="dbElem.*"%>
<%@page import="dbInteractions.*"%>
<%@page import="wrap.*"%>



<html xmlns="http://www.w3.org/1999/xhtml">
<body>

	<% 
	final String user = "aleph";
	final String pw = "aleph";
	String qid = request.getParameter("qid");
	boolean flag = true;
	if (qid != null) {
		flag = false;
	}
	String name = request.getParameter("name");
	String endURL = request.getParameter("endURL");
	String startURL = request.getParameter("startURL");
	String protocol_id = request.getParameter("protocol_id");
	String time = request.getParameter("time");
	String date = request.getParameter("date");
	String datetime = date+' '+time;
	String reg = request.getParameter("reg");
	String struct_loc = request.getParameter("struct");
%>
	<table width="100%">

		<tr>
			<%if(flag) {%>
			<td align="left">
				<form action="choose_protocol.jsp">
					<input type="submit" value="Создать еще один запрос">
				</form>
			</td>
			<% 
					}
					else {%>
			<td align="left">
				<form action="node_info.jsp">
					<input type="hidden" value="<%=qid%>" name="qid"><input
						type="submit" value="Вернуться к информации о запросе">
				</form>
			</td>
			<%} %>
			<td align="right">
				<form action="nodes.jsp">
					<input type="submit" value="Вернуться к просмотру запросов">
				</form>
			</td>
		</tr>
	</table>
	<table>
		<%
	if (name.compareTo("")==0) {
%>


		<tr>Поле, содержащее имя запроса, не должно быть пустым
		</tr>
		<br>
		<%		
	}
	if (endURL.compareTo("")==0) {
		%>
		<tr>Поле, содержащее URL конечного узла, не должно быть пустым
		</tr>
		<br>
		<%		
			}
 	if (startURL.compareTo("")==0) {
		%>
		<tr>Поле, содержащее URL точки доступа, не должно быть пустым
		</tr>
		<br>
		<%		
			}
	Query qr = new Query();
	
	if (!flag) {
		qr.setId(qid);
		
	}
	qr.setName(name);
	qr.setEndURL(endURL);
	qr.setStartURL(startURL);
	qr.setProtocol_id(protocol_id);
	qr.setTime(datetime);
	qr.setReg(reg);
	qr.setStruct_loc(struct_loc);
	ServiceMessage srvMsg = new ServiceMessage();
	StringMessage strMsg = null;
	try {
		if (flag) {
			strMsg = ManagerService.addQuery(user, pw, qr);
			srvMsg = strMsg;
			qid = strMsg.getData();
		}
		else
			srvMsg = ManagerService.updateQuery(user, pw, qr);
	} catch (Exception e) {
		
	}
	
	if (srvMsg.getText() == null) {
%>
		<tr>Харвестер не доступен
		</tr>
		<%	
	} else {
		if (srvMsg.getCode() == 1) {
			if (flag) {
				
				
%>
		<tr>Запрос успешно добавлен
		</tr>
		<%
			} else {
%>
		<tr>Изменения успешно сохранены
		</tr>
		<%				
			}
			
		} else {
%>
		<tr><%=srvMsg.getText()%></tr>
		<%
		}
	}	
%>
	</table>
</body>
</html>



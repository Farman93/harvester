<%@ page language="java" contentType="text/html;charset=UTF-8"%>


<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@page import="services.ManagerService"%>
<%@page import="dbElem.*"%>
<%@page import="dbInteractions.*"%>
<%@page import="wrap.*"%>
<!--  <meta http-equiv="Refresh" content="20; URL=/harvesting/nodes.html"> -->
<html xmlns="http://www.w3.org/1999/xhtml">
<body>






	<table cellpadding="5" cellspacing="0" align="center">
		<tr>
			<td colspan="8"><br>
				<h1>Просмотр текущих запросов</h1></td>
		</tr>
		<% 
	final String user = "aleph";
	final String pw = "aleph";
	QueryMessage qrMsg = new QueryMessage();
	try {
		qrMsg = ManagerService.getQueriesForUser(user, pw);
	} catch (Exception e) {
		
	}
	
	if (qrMsg.getText() == null ) {
		
%>
		<tr>
			<td align="center" colspan="8">Харвестер не доступен</td>
		</tr>
		<% 
	} else {
		Query[] qrArr = qrMsg.getQueryArray();
		if (qrMsg.getCode() != 1) {
%>
		<tr>
			<td align="center" colspan="8"><%=qrMsg.getText()%></td>
		</tr>
		<%	
		} else {
%>
		<tr>
			<td width="10%"><b>Имя</b></td>
			<td width="20%"><b>URL конечного узла</b></td>
			<td width="20%"><b>URL точки доступа</b></td>
			<td width="10%"><b>Протокол</b></td>
			<td width="20%"><b>Время начала</b></td>
			<td width="5%"><b>Регулярность</b></td>
			<td width="10%"><b>Структура</b></td>
			<td width="5%"><b>Активность</b></td>
		</tr>
		<%
			for (int i = 0; i < qrArr.length; ++i) {
%>
		<tr>
			<td width="11%"><%=qrArr[i].getName()%></td>
			<td width="17%"><%=qrArr[i].getEndURL()%></td>
			<td width="17%"><%=qrArr[i].getStartURL()%></td>
			<%
		StringMessage strMsg = new StringMessage();
			try { strMsg = ManagerService.getProtocolNameForId(user,pw,Integer.parseInt(qrArr[i].getProtocol_id())); } catch (Exception e) {}
	%>
			<td width="10%"><%=strMsg.getData()%></td>
			<td width="15%"><%=qrArr[i].getTime()%></td>
			<td width="5%"><%=qrArr[i].getReg()%></td>
			<td width="15%"><%=qrArr[i].getStruct_loc()%></td>
			<td width="15%"><%=qrArr[i].getActive()%></td>
			<td width="10%"><form action="node_info.jsp">
					<input type="hidden" value="<%=qrArr[i].getId()%>" name="qid"><input
						type="submit" value="Подробнее">
				</form></td>
		</tr>
		<%					
			}
%>

		<%
		}
	}
%>
	</table>

	<table width="100%">

		<tr>
			<td align="center"><a href="choose_protocol.jsp">Создать
					новый запрос</a></td>
		</tr>
	</table>
</body>
</html>



















<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="services.ManagerService"%>
<%@page import="dbElem.*"%>
<%@page import="dbInteractions.*"%>
<%@page import="wrap.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<table width="100%">

		<tr>
			<td align="right"><a href="nodes.jsp">Вернуться к просмотру
					текущих запросов</a></td>
		</tr>
	</table>
	<table cellpadding="5" cellspacing="0" align="center">
		<% 
final String user = "aleph";
final String pw = "aleph";

String qid = request.getParameter("qid");
QueryMessage qrMsg = new QueryMessage();
try {
	qrMsg = ManagerService.getQueryInfo(user, pw, Integer.parseInt(qid));
} catch (Exception e) {
	
}
if (qrMsg.getText() == null) {
%>
		<tr>Харвестер не доступен
		</tr>
		<%	
} else {
%>

		<%
	
	if (qrMsg.getCode() == 1) {
		Query[] qrArr = qrMsg.getQueryArray();
%>

		<tr>
			<td colspan="8"><br>
				<h1>Информация о запросе</h1></td>
		</tr>
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
		<tr>
			<td width="11%"><%=qrArr[0].getName()%></td>
			<td width="17%"><%=qrArr[0].getEndURL()%></td>
			<td width="17%"><%=qrArr[0].getStartURL()%></td>
			<%	StringMessage strMsg = new StringMessage();
		try { strMsg = ManagerService.getProtocolNameForId(user,pw,Integer.parseInt(qrArr[0].getProtocol_id())); } catch (Exception e) {}%>
			<td width="10%"><%=strMsg.getData()%></td>
			<td width="15%"><%=qrArr[0].getTime()%></td>
			<td width="5%"><%=qrArr[0].getReg()%></td>
			<td width="15%"><%=qrArr[0].getStruct_loc()%></td>
			<td width="15%"><%=qrArr[0].getActive()%></td>
		</tr>
	</table>
	<table cellpadding="5" cellspacing="0" align="center">
		<tr>
			<td width="25%"><form action="node_update.jsp">
					<input type="hidden" value="<%=qrArr[0].getId()%>" name="qid"><input
						type="submit" value="Редактировать">
				</form></td>
			<td width="25%"><form action="change_active.jsp">
					<input type="hidden" value="<%=qrArr[0].getId()%>" name="qid"><input
						type="hidden" value="1" name="act"><input type="submit"
						value="Активировать">
				</form></td>
			<td width="25%"><form action="change_active.jsp">
					<input type="hidden" value="<%=qrArr[0].getId()%>" name="qid"><input
						type="hidden" value="0" name="act"><input type="submit"
						value="Деактивировать">
				</form></td>
			<td width="25%"><form action="node_delete.jsp">
					<input type="hidden" value="<%=qrArr[0].getId()%>" name="qid"><input
						type="submit" value="Удалить">
				</form></td>
		</tr>
	</table>
	<table>
		<tr>
			<td colspan="4"><br>
				<h1>Неудачные попытки</h1></td>
		</tr>
		<% 
	ScheduleMessage schMsg = new ScheduleMessage();
	try { schMsg = ManagerService.getFailedAttemptsForQuery(user,pw,Integer.parseInt(qrArr[0].getId())); } catch (Exception e) {}
	ScheduleElement[] schArr = schMsg.getScheduleArray();
	%>
		<tr>
			<td width="40%"><b>Дата</b></td>
			<td width="10%"><b>Номер попытки</b></td>
			<td width="30%"><b>Сообщение</b></td>
			<td width="20%"><b>Код ошибки</b></td>
		</tr>
		<%
if (schMsg.getCode() != 1) {
%>
		<tr>
			<td colspan="4" align="center">Нет неудачных попыток</td>
		</tr>
		<%				
}
			else {
				for (int i = 0; i < schArr.length; ++i) {
		
%>
		<tr>
			<td width="40%"><%=schArr[i].getDatetime()%></td>
			<td width="10%"><%=schArr[i].getAttempt()%></td>
			<td width="30%"><%=schArr[i].getMessage()%></td>
			<td width="20%"><%=schArr[i].getResult()%></td>
		</tr>
		<%					
			}
		}
%>

		<%
	} else {
%>
		<tr><%=qrMsg.getText()%></tr>
		<%
	}
}	
%>
	</table>
</body>
</html>

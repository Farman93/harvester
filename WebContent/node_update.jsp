<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="services.ManagerService"%>
<%@page import="dbElem.*"%>
<%@page import="dbInteractions.*"%>
<%@page import="wrap.*"%>




<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
$(function() {
$( "#datepicker" ).datepicker({ dateFormat: "dd.mm.yy" });
});
</script>

<body>

	<% 
	final String user = "aleph";
	final String pw = "aleph";
	String qid = request.getParameter("qid");
	QueryMessage qrMsg = new QueryMessage();
try {
	qrMsg = ManagerService.getQueryInfo(user, pw, Integer.parseInt(qid));
} catch (Exception e) {
	
}
%>
	<table width="100%">
		<tr>
			<td align="left">
				<form action="node_info.jsp">
					<input type="hidden" value="<%=qid%>" name="qid"><input
						type="submit" value="Вернуться к информации о запросе">
				</form>
			</td>
			<td align="right">
				<form action="nodes.jsp">
					<input type="hidden" value="<%=qid%>" name="qid"><input
						type="submit" value="Вернуться к просмотру запросов">
				</form>
			</td>
		</tr>
	</table>

	<%
if (qrMsg.getText() == null) {
	
%>
	<tr>Харвестер не доступен
	</tr>
	<%	
} else {
	ServiceMessage srvMsg = new ServiceMessage();
	ProtocolMessage prtMsg = ManagerService.getProtocols(user, pw);
	Protocol[] prArr = prtMsg.getProtocolArray();
	if (qrMsg.getCode() == 1) {
		Query[] qrArr = qrMsg.getQueryArray();
%>
	<table cellpadding="5" cellspacing="0" align="center">
		<tr>
			<td colspan="7"><br>
				<h1>Редактирование запроса</h1></td>
		</tr>
		<form method="post" action="handler.jsp">
			<input type="hidden" name="qid" value="<%=qid%>">
			<tr>
				<td><strong>Имя</strong></td>
				<td><input type="text" size="40" name="name"
					value="<%=qrArr[0].getName()%>"></td>
			</tr>
			<br>
			<tr>
				<td><strong>URL конечного узла</strong></td>
				<td><input type="text" size="40" name="endURL"
					value="<%=qrArr[0].getEndURL()%>"></td>
			</tr>
			<br>
			<tr>
				<td><strong>URL точки доступа</strong></td>
				<td><input type="text" size="40" name="startURL"
					value="<%=qrArr[0].getStartURL()%>"></td>
			</tr>
			<br>
			<% if (prArr == null) {
	%>
			<tr>Нет протоколов для харвестинга
			</tr>
			<%
			} else {
	%>
			<tr>
				<td><strong>Протокол</strong></td>
				<td><select size=1 name="protocol_id">
						<% for (int i = 0; i < prArr.length; ++i) {%>
						<option value="<%=prArr[i].getId()%>"><%=prArr[i].getName()%></option>
						<% }%>
				</select></td>
			</tr>
			<br>
			<tr>
				<td><strong>Время запуска</strong></td>
				<td><input type="text" size="40" name="time"
					value="<%=qrArr[0].getTime().substring(11,16)%>"></td>
			</tr>
			<br>
			<tr>
				<td><strong>Дата первого запуска</strong></td>
				<td><input type="text" id="datepicker" name="date"
					value="<%=qrArr[0].getTime().substring(0,10)%>"></td>
			</tr>
			<br>
			<tr>
				<td><strong>Регулярность</strong></td>
				<td><input type="text" size="40" name="reg"
					value="<%=qrArr[0].getReg()%>"></td>
			</tr>
			<br>
			<tr>
				<td><strong>Структура</strong></td>
				<td><textarea rows="10" cols="40" name="struct_loc"><%=qrArr[0].getStruct_loc()%></textarea></td>
			</tr>
			<br>
			<tr>
				<td><input type="submit" value="Сохранить"></td>
				<td><input type="reset" value="Очистить"></td>
			</tr>
		</form>
		<% 
		}
	}
}
%>
	</table>
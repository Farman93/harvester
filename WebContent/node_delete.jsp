<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@page import="services.ManagerService"%>
<%@page import="dbElem.*"%>
<%@page import="dbInteractions.*"%>
<%@page import="wrap.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>

<body>
	<table>
		<tr>
			<td align="right"><a href="nodes.jsp">Вернуться к просмотру
					текущих запросов</a></td>
		</tr>
		<% 
	final String user = "aleph";
	final String pw = "aleph";
	String qid = request.getParameter("qid");
	ServiceMessage srvMsg = new ServiceMessage();
	try {
		srvMsg = ManagerService.deleteQuery(user, pw, Integer.parseInt(qid));
	} catch (Exception e) {
	}
	if (srvMsg.getText() == null) {
%>
		<tr>Харвестер не доступен
		</tr>
		<%	
	} else {
		if (srvMsg.getCode() == 1) {
%>
		<tr>Запрос успешно удален
		</tr>
		<% } else {%>
		<tr><%=srvMsg.getText()%></tr>
		<%}}%>
	</table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>



<%@ page import="java.math.*"%>
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

<body>





	<table width="100%">

		<tr>
			<td align="right"><a href="nodes.jsp">Назад</a></td>
		</tr>
	</table>

	<table cellpadding="5" cellspacing="0" align="center">
		<tr>
			<td colspan="2"><br>
				<h1>Выбор протокола</h1></td>
		</tr>
		<% 
		final String user = "aleph";
		final String pw = "aleph";
		String accessPoint = "";
		ProtocolMessage prtMsg = ManagerService.getProtocols(user,pw);		
		if (prtMsg.getCode() != 1) {
	%>
		<tr>Харвестер не доступен
		</tr>
		<%	
		} else {
			Protocol[] prArr = prtMsg.getProtocolArray();
			if (prArr == null) {
	%>
		<tr>Нет протоколов для харвестинга
		</tr>
		<%
			} else {
	%>
		<form method="post" action="node.jsp">
			<tr>
				<td><strong>Протокол</strong></td>
				<td><select size=1 name="protocol_id">
						<% for (int i = 0; i < prArr.length; ++i) {%>
						<option value="<%=prArr[i].getId()%>"><%=prArr[i].getName()%></option>
						<% }}}%>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="Создать"></td>
			</tr>
	</table>
</body>
</html>
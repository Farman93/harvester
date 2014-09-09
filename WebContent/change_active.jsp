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
	String act = request.getParameter("act");
	String qid = request.getParameter("qid");
	ServiceMessage qrMsg = new ServiceMessage();
	
	boolean flag = act.compareTo("1")==0;
	if (flag) {
		try {
			qrMsg = ManagerService.activateQuery(user, pw, Integer.parseInt(qid));
		} catch (Exception e) {
		}
	} else {
		try {
			qrMsg = ManagerService.deactivateQuery(user, pw, Integer.parseInt(qid));
		} catch (Exception e) {
		}
	}
	if (qrMsg.getText() == null) {
		%>
	<tr>Харвестер не доступен
	</tr>
	<%	
	} else {
		

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
	<table>
		<%
if (flag) {
%>
		<tr>Запрос успешно активирован
		</tr>
		<%
} else {
%>
		<tr>Запрос успешно деактивирован
		</tr>
		<%
	} 
}
%>
	</table>
</body>
</html>

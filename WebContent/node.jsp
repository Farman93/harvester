<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.math.*"%>
<%@ page import="java.util.*"%>
<%@ page import="dbElem.*"%>
<%@ page import="services.*"%>
<%@ page import="wrap.*"%>





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
<%
	final String user = "aleph";
	final String pw = "aleph";
	if (request.getMethod().compareTo("POST") == 0) {
		String protocol_id = request.getParameter("protocol_id");
		ProtocolMessage prtMsg = new ProtocolMessage();
		try {
			prtMsg = ManagerService.getProtocolForId(user,pw,Integer.parseInt(protocol_id)); 
		} 
		catch (Exception e) {System.err.println("node.jsp Exception");}
%>


<body>



	<table width="100%">

		<tr>
			<td align="right"><a href="nodes.jsp">Назад</a></td>
		</tr>
	</table>

	<table cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td colspan="2"><br>
				<h1>Регистрация запроса</h1></td>
		</tr>
		<% 
		String accessPoint = "";
		
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
		<form method="post" action="handler.jsp">

			<tr>
				<td><strong>Имя</strong></td>
				<td><input type="text" size="40" name="name"></td>
			</tr>
			<br>
			<tr>
				<td><strong>URL конечного узла</strong></td>
				<td><input type="text" size="40" name="endURL"></td>
			</tr>
			<br>
			<tr>
				<td><strong>URL точки доступа</strong></td>
				<td><input type="text" size="40" name="startURL"
					value="<%=accessPoint%>"></td>
			</tr>


			<input type="hidden" name="protocol_id" value="<%=prArr[0].getId()%>">

			<tr>
				<td><strong>Время запуска</strong></td>
				<td><input type="text" size="40" name="time" value="03:00"></td>
			</tr>
			<br>
			<tr>
				<td><strong>Дата первого запуска</strong></td>
				<td><input type="text" id="datepicker" name="date"></td>
			</tr>
			<br>


			<tr>
				<td><strong>Регулярность</strong></td>
				<td><input type="text" size="40" name="reg"></td>
			</tr>
			<br>



			<tr>
				<td><strong>Дополнительные параметры для протокола</strong></td>
				<td><textarea name="struct" cols="40" rows="20"><%=prArr[0].getXml()%></textarea>
					</p></td>
			</tr>
			<br>
			<tr>
				<td><input type="submit" value="Создать"></td>
				<td><input type="reset" value="Очистить"></td>
			</tr>
		</form>
		<% 
			}
		}
	}
	%>
	</table>
</body>
</html>

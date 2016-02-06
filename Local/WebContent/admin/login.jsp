<%@page import="in.sontx.web.local.controller.BaseServlet"%>
<%@page import="in.sontx.web.local.bean.AdminAccount"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login to admin region</title>
<script type="text/javascript">
	function requestUserInput() {
		var firstElement = loginForm.username;
		if (firstElement != null)
			firstElement.focus();
	}
</script>
<%
	request.setAttribute("categoryName", "Login");
%>
</head>
<body onload="requestUserInput();">
	<%@include file="../_helper.jspf"%>
	<div class="content">
		<%
			if (_request.hasSessionAttribute("account", AdminAccount.class)) {
				BaseServlet.forward(request, response, "manager?req=sm");
			} else {
		%>
		<form name="loginForm" action="manager" method="post">
			<input type="hidden" name="req" value="cl">
			<center>
				<table>
					<tr>
						<th>Username:</th>
						<th><input type="text" name="username"></th>
					</tr>
					<tr>
						<th>Password:</th>
						<th><input type="password" name="password"></th>
					</tr>
					<tr>
						<th></th>
						<th align="right"><input type="submit" value="Login"></th>
					</tr>
				</table>
			</center>
		</form>
		<%
			}
		%>
	</div>
</body>
</html>
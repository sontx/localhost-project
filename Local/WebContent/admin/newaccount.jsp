<%@page import="in.sontx.web.local.controller.BaseServlet"%>
<%@page import="in.sontx.web.local.bean.AdminAccount"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create new account</title>
<script type="text/javascript">
	function eventFire(el, etype) {
		if (el.fireEvent) {
			el.fireEvent('on' + etype);
		} else {
			var evObj = document.createEvent('Events');
			evObj.initEvent(etype, true, false);
			el.dispatchEvent(evObj);
		}
	}
	function goback() {
		eventFire(document.getElementById('hiddenLink'), 'click');
	}
</script>
</head>
<body>
	<%@include file="../_helper.jspf"%>
	<%
		if (_request.hasSessionAttribute("account", AdminAccount.class)) {
	%>
	<center>
		<form action="manager" method="post">
			<input type="hidden" name="req" value="cn"> 
			<a id="hiddenLink" href="manager"></a>
			<table>
				<tr>
					<td>User name:</td>
					<td><input id="username" type="text" name="username" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input id="password" type="password" name="password" /></td>
				</tr>
				<tr>
					<td>Confirm:</td>
					<td><input id="confirm" type="password" name="confirm" /></td>
				</tr>
				<tr>
					<td />
					<td align="right">
						<input type="button" value="Cancel" onclick="goback();">
						<input type="submit" value="Create">
					</td>
				</tr>
			</table>
		</form>
	</center>
	<%
		} else {
			BaseServlet.forward(request, response, "../manager?req=sl");
		}
	%>
</body>
</html>
<%@page import="in.sontx.web.local.bean.AdminAccount"%>
<%@page import="in.sontx.web.local.controller.BaseServlet"%>
<%@page import="in.sontx.web.shared.utils.DateTime"%>
<%@page import="in.sontx.web.local.bean.AccountInfo"%>
<%@page import="in.sontx.web.local.service.ResourceManager"%>
<%@page import="in.sontx.web.local.bean.Account"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manager account</title>
<script type="text/javascript">
function changePassword(id) {
	var newpass = prompt("Enter new password for id " + id, "");
	if (newpass != null) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			 if (xhttp.readyState == 4 && xhttp.status == 200) {
			 	if (xhttp.responseText != "OK")
			 		alert("Can not change password!");
			 	else
			 		alert("Changed!");
			 }
		};
		xhttp.open("POST", "manager", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send("req=cp&np=" + newpass + "&userid=" + id);
	}
	return false;
}
</script>
</head>
<body>
	<%@include file="../_helper.jspf"%>
	<a href="manager?req=sn">Add new...</a>
	<a style="right: 0%" href="manager?req=lo">Logout</a>
	<center>
		<%
			if (_request.hasSessionAttribute("account", AdminAccount.class)) {
				if (_request.hasAttribute("result", Boolean.class)) {
					boolean result = (Boolean) _request.getAttribute("result");
					_request.removeAttribute("result");
					if (!result) {
		%>
			<span style="color: red">Can not create new account!</span>
			<br/>
		<%
					}
				}
				List<Account> accounts = ResourceManager.getInstance().globalMgr.getAccounts();
				if (accounts == null || accounts.size() == 0) {
		%>
		No account!
		<%
				} else {
		%>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>User name</th>
				<th>User dir</th>
				<th>Last login</th>
				<th>Task</th>
			</tr>
			<%
					for (int i = 0; i < accounts.size(); i++) {
						Account account = accounts.get(i);
						AccountInfo info = ResourceManager.getInstance().globalMgr.getAccountInfo(account.getUserId());
						if (info != null) {
			%>
			<tr>
				<td><%=account.getUserId()%></td>
				<td><%=account.getUserName()%></td>
				<td><%=info.getUserDir()%></td>
				<td><%=DateTime.parse(info.getLastLogin()).toString()%></td>
				<td>
					<a href="manager" onclick="return changePassword(<%=account.getUserId()%>)">Password</a>
					<a href="manager?req=da&userid=<%=account.getUserId()%>" onclick="return confirm('Delete <%=account.getUserName()%>, sure?')">Delete</a>
				</td>
			</tr>
			<%
						}
					}
			%>
		</table>
		<%
				}
			} else {
				BaseServlet.forward(request, response, "../manager?req=sl");
			}
		%>
	</center>
</body>
</html>
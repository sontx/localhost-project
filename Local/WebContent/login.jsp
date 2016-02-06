<%@page import="in.sontx.web.local.Config"%>
<%@page import="in.sontx.web.local.controller.BaseServlet"%>
<%@page import="in.sontx.web.local.bean.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="icon" href="../favicon.ico">
<%@include file="../_helper_non_theme.jspf"%>
<title>Login to <%=Config.WEB_NAME%></title>
<link href="../css/login.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<%
			if (!_request.hasSessionAttribute("account", Account.class)) {
				if ((Boolean) _request.popAttribute("lastError", Boolean.FALSE)) {
		%>
		<script>
			$(document).ready(function() {
				bootbox.alert('<span style="color: red"><b>Username or password is entered incorrectly!</b></span>', function() {
					
				});
			});
		</script>
		<%
				}
		%>
		<form class="form-signin thumbnail" action="login" method="post">
			<h3 class="form-signin-heading"> Welcome to <%=Config.WEB_NAME%> </h3>
			<input type="hidden" name="req" value="cl">
			<input type="text" id="username" name="username" class="form-control" pattern="<%=Config.ACCOUNT_USERNAME_PATTERN%>" placeholder="Username has 3 to 20 characters" required autofocus> <input type="password" id="password" name="password" class="form-control" pattern="<%=Config.ACCOUNT_PASSWORD_PATTERN%>" placeholder="Password has 6 to 40 characters" required>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me"> Remember me </label>
			</div>

			<div class="form-action-div col-md-12">
				<a class="col-xs-2 form-action-first help-block" href="">Register</a> 
				<a class="col-xs-2 help-block" href="">Forgot?</a> 
				<input type="submit" class="btn btn-primary pull-right" value="Sign in">
			</div>

		</form>
		<%
			} else {
				Account account = (Account) _request.getSessionAttribute("account");
		%>
		<h3>You are logged with user name is <%=account.getUserName()%></h3>
		<%
			}
		%>
	</div>

</body>
</html>
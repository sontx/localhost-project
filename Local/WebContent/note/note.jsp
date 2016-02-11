<%@page import="in.sontx.web.local.bean.Account"%>
<%@page import="in.sontx.web.shared.utils.Convert"%>
<%@page import="in.sontx.web.local.controller.BaseServlet"%>
<%@page import="in.sontx.web.local.bean.Note"%>
<%@page import="java.util.List"%>
<%@page import="in.sontx.web.shared.utils.DateTime"%>
<%@page import="in.sontx.web.local.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="icon" href="../favicon.ico">
	<title>Quick note</title>
	<link rel="stylesheet" type="text/css" href="../css/utils.css" />
</head>
<body class="bb-js">
	<%
		request.setAttribute("activeId", new Integer(0));
		request.setAttribute("activeName", "Quick Note");
		request.setAttribute("activeDescription", "Create and manage notes quickly");
	%>
	<%@include file="../_header.jspf"%>
	<script src="../note/note.js"></script>

	<div class="container">
		<div class="text-center page-header">
			<p class="hidden-xs bb-code code-leader">
				printf(<span class="bb-code-string">"Start your day with a note!"</span>);
			</p>
			<p>
				<button class="btn btn-primary btn-lg  bb-light-blue" onclick="createNote();">Create now...</button>
			</p>
		</div>
		<div class="row">
			<div class="col-md-12">
				<form class="form-inline" role="form">
					<p>
						<label class="hidden-xs">Search your note:</label> 
						<input type="search" class="form-control" name="s" placeholder="Looking for something...">
						<input type="submit" class="btn btn-success hidden-xs" value="Search">
					</p>
				</form>
				<table class="table table-hover" style="margin: 40px 0 20px;">
					<%
						@SuppressWarnings("unchecked")
						List<Note> notes = (List<Note>) _request.getAttribute("notes");
						if (notes != null && !notes.isEmpty()) {
							for (Note note : notes) {
					%>
					<tbody>
						<tr onclick="showNote(this);">
							<td>
								<pre class="box-wrapable box-no-style" id="td_title"><%=Convert.toHtml(note.getTitle())%></pre>
								<pre id="td_content" class="box-wrapable"><%=Convert.toHtml(note.getContent())%></pre>
							</td>
							<td class="hidden-xs" id="td_created" style="white-space: nowrap; text-align: center; vertical-align: middle;"><%=DateTime.parse(note.getCreated())%></td>
							<td id="td_id" style="display: none"><%=note.getId()%></td>
						</tr>
					</tbody>
					<%
							}
						} else {
					%>
					<caption>No note here!</caption>
					<%
					}
					%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
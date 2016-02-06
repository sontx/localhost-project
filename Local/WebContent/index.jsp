<%@page import="in.sontx.web.local.controller.BaseServlet"%>
<%@page import="in.sontx.web.local.bean.Account"%>
<%@page import="in.sontx.web.local.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="../css/index.css" />
<title><%=Config.WEB_NAME%> project</title>
</head>
<body class="bb-js">
    <%@include file="../_header.jspf"%>

    <div class="container">
        <section class="bb-section">
            <div>
                <img alt="Brief about project" src="../images/brief_proj.png" class="img-responsive center-block">
            </div>
            <div>
                <ul class="ch-grid">
                    <li onclick="redirect('note');">
                        <div class="ch-item ch-img-1">
                            <div class="ch-info">
                                <h3>Note</h3>
                                <p>Create notes quickly</p>
                            </div>
                        </div>
                    </li>
                    <li onclick="redirect('shared');">
                        <div class="ch-item ch-img-2">
                            <div class="ch-info">
                                <h3>Shared</h3>
                                <p>Share files by everyone</p>
                            </div>
                        </div>
                    </li>
                    <li onclick="redirect('serect');">
                        <div class="ch-item ch-img-3">
                            <div class="ch-info">
                                <h3>Serect</h3>
                                <p>Put your files in to private place</p>
                            </div>
                        </div>
                    </li>
                    <li onclick="redirect('codebin');">
                        <div class="ch-item ch-img-4">
                            <div class="ch-info">
                                <h3>Codebin</h3>
                                <p>Type code and share for your friends</p>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
    </div>
</body>
</html>
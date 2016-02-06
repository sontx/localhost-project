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
    <title>Welcome to <%=Config.WEB_NAME%> project</title>
</head>
<body class="bb-js">
    <%@include file="../_helper.jspf"%>
    <%
    	if (_request.hasSessionAttribute("account", Account.class)) { BaseServlet.sendRedirect(_response, "index"); return;}
    %>
    <div class="navbar navbar-inverse" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-bb-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a id=top class="navbar-brand navbar-brand-active" href="./"><%=Config.WEB_NAME%></a>
            </div>
            <div class="collapse navbar-collapse navbar-bb-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#getting-started">Getting Started</a></li>
                    <li><a href="#dependencies">Dependencies</a></li>
                    <li><a href="#usage">Usage</a></li>
                    <li><a href="shared">Shared</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="login">Login</a>
                    </li>
                    <li>
                        <a href="#" onclick="return registerAccount('<%=Config.ADMIN_EMAIL%>');">Register</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div id="content" class="bb-docs-header" tabindex="-1">
        <div class="container text-center">
            <div class="bb-masthead">
                <h1><%=Config.WEB_NAME%></h1>
                <p>
                    Keep everything in your computer.
                </p>
            </div>
            <div class="bb-meta">
                <ul class="bb-meta-list">
                    <li>
                        <span>Version 1.0.0</span>
                    </li>
                    <li>&middot;</li>
                    <li>
                        <a href="#download">Download</a>
                    </li>
                    <li>&middot;</li>
                    <li>
                        <a href="https://github.com">Github project</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="container">

        <section class="bb-section">
            <div class="lead-top">
                <p class="lead">
                    <b><%=Config.WEB_NAME%></b> is a dynamic Java web project base on <a href="https://en.wikipedia.org/wiki/JavaServer_Pages">JSP</a>/<a href="https://en.wikipedia.org/wiki/Java_servlet">Servlet</a> technology, it provides some useful functions same as notes, upload/shared files or compile/run simple code and so on. Especially, it is designed to run on PCs/Raspberry Pis so you can keep everything in your local computer and everyone could access your local website via Internet. Don't worry about deploy, managing or publish it to Internet, everything are very simple so anyone could use it.
                </p>
                <div class="text-center">
                    <p>
                        <a href="#download" data-bb="alert" class="bb-trigger btn btn-primary btn-lg  bb-light-blue">Download Now</a>
                    </p>
                </div>
            </div>
        </section>

        <section id="getting-started" class="bb-section">
            <div class="page-header">
                <h2>Core Functions</h2>
            </div>
            <p>
                Current project support four useful functions are:
            </p>
            <ul>
                <li><a href="#func-note">Quick note: </a>You can create a simple note include a title and text content. You must take an account before use this function.</li>
                <li><a href="#func-shared">Shared files: </a>You can upload to this host then you can share files for everyone. You don't need an account to see shared files, anyone can see it but you must have the account when you want share you files.</li>
                <li><a href="#func-upload">Upload files: </a>You can upload many files to this host for saving and only you can see them. After you uploaded them you can share them to everyone anytime you want.</li>
                <li><a href="#func-codebin">Codebin: </a>Same as <a href="http://pastebin.com">pastebin.com</a> or <a href="http://codepad.org">codepad.org</a>, you can save then shared for your friends or compile then run a simple code.</li>
            </ul>
            <p>
                Above is a brief description about current support functions, some functions require a login before you use it so you must get an account to use all functions which being supported by this website(contact administrator to take own account).
            </p>
        </section>

        <section id="functions" class="bb-section">
            <div class="page-header">
                <h2>Functions detail</h2>
            </div>
            <p>
                Below is core functions detail, a short function description and screen-shots will help you are easy to understand.
            </p>
            <div id="func-note" class="row">
                <div class="col-md-12">
                    <p class="lead">Quick note</p>
                    <p>
                        So, this part is very easy to understand. Each account will can save, edit, delete and search their own notes. Each note include a title, text content and created time. Notes will sort by modification time when they show. There are some screen-shots about this functions:
                    <p>
                    <img src="">
                </div>
            </div>
        </section>

    </div>
</body>
</html>
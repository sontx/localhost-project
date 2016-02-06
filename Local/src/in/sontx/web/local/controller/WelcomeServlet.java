package in.sontx.web.local.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import in.sontx.web.local.bean.Account;
import in.sontx.web.shared.net.www.HttpServletRequestWrapper;
import in.sontx.web.shared.net.www.HttpServletResponseWrapper;

@WebServlet("/welcome")
public class WelcomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		if (request.hasSessionAttribute("account", Account.class))
			sendRedirect(response, "index");
		else
			forward(request, response, "welcome.jsp");
	}

}

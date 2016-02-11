package in.sontx.web.local.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import in.sontx.web.local.bean.Account;
import in.sontx.web.local.service.ResourceManager;
import in.sontx.web.shared.net.www.HttpServletRequestWrapper;
import in.sontx.web.shared.net.www.HttpServletResponseWrapper;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		String req = request.getParameter("req");
		if ("logout".equals(req)) {// logout
			request.deleteSession();
			sendRedirect(response, "welcome");
		} else if ("cl".equals(req)) {// check login
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Account account = ResourceManager.getInstance().getAccountMgr().getAccount(username, password);
			if (account != null) {
				request.createSession();
				request.setSessionAttribute("account", account);
				ResourceManager.getInstance().generateUserSession(account, request.getSession().getId());
				sendRedirect(response, "index");
			} else {
				request.deleteSession();
				request.setAttribute("lastError", true);
				forward(request, response, "login.jsp");
			}
		} else {// show login page
			forward(request, response, "login.jsp");
		}
	}

}

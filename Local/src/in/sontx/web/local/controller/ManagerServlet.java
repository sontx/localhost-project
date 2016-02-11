package in.sontx.web.local.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import in.sontx.web.local.bean.AdminAccount;
import in.sontx.web.local.service.ResourceManager;
import in.sontx.web.shared.net.www.HttpServletRequestWrapper;
import in.sontx.web.shared.net.www.HttpServletResponseWrapper;
import in.sontx.web.shared.net.www.Rule;
import in.sontx.web.shared.net.www.SimpleRule;

@WebServlet("/manager")
public class ManagerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private Rule adminRule = new AdminRule();

	private boolean createAccount(String username, String password) {
		return ResourceManager.getInstance().getAccountMgr().createAccount(username, password);
	}

	private boolean checkAdminLogin(String username, String password) {
		return true;
	}

	private boolean changePassword(String userId, String newPassword) {
		return ResourceManager.getInstance().getAccountMgr().changePassword(userId, newPassword);
	}

	private boolean deleteAccount(String userId) {
		return ResourceManager.getInstance().getAccountMgr().deleteAccount(userId);
	}

	private void showLogin(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		if (!request.hasSessionAttribute("account", AdminAccount.class)) 
				request.deleteSession();
		forward(request, response, "admin/login.jsp");
	}

	private void showManager(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		forward(request, response, "admin/manager.jsp");
	}

	private void showNewAccount(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		forward(request, response, "admin/newaccount.jsp");
	}

	private void checkLogin(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (checkAdminLogin(username, password)) {
			request.createSession();
			AdminAccount account = new AdminAccount();
			account.setUserName(username);
			account.setPassword(password);
			request.setSessionAttribute("account", account);
			forward(request, response, "admin/manager.jsp");
		} else {
			sendRedirect(response, "manager?req=sl");
		}
	}

	private void createNewAccount(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		Boolean result = createAccount(request.getParameter("username"), request.getParameter("password"));
		request.setAttribute("result", result);
		sendRedirect(response, "manager");
	}

	private void changePassword(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		String newPassword = request.getParameter("np");
		String userId = request.getParameter("userid");
		boolean result = changePassword(userId, newPassword);
		responseResult(response, result ? RESULT_OK : RESULT_FAIL);
	}

	private void deleteAccount(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		String userId = request.getParameter("userid");
		deleteAccount(userId);
		sendRedirect(response, "manager");
	}

	private void logout(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		request.deleteSession();
		showLogin(request, response);
	}

	@Override
	protected void doWork(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		if (adminRule.executable(request)) {
			String req = request.getParameter("req");
			if ("sl".equals(req)) {// show login page
				showLogin(request, response);
			} else if ("sm".equals(req)) {// show manager page
				showManager(request, response);
			} else if ("sn".equals(req)) {// show create new account page
				showNewAccount(request, response);
			} else if ("cl".equals(req)) {// check login
				checkLogin(request, response);
			} else if ("cn".equals(req)) {// create new account
				createNewAccount(request, response);
			} else if ("cp".equals(req)) {// change password
				changePassword(request, response);
			} else if ("da".equals(req)) {// delete account
				deleteAccount(request, response);
			} else if ("lo".equals(req)) {// logout
				logout(request, response);
			} else {
				showManager(request, response);
			}
		} else {
			showLogin(request, response);
		}
	}

	private static class AdminRule extends SimpleRule {

		@Override
		protected void setupTaskMap(HashMap<String, Boolean> taskMap) {
			taskMap.put("sl", true);
			taskMap.put("sm", false);
			taskMap.put("sn", false);
			taskMap.put("cl", true);
			taskMap.put("cn", false);
			taskMap.put("cp", false);
			taskMap.put("da", false);
			taskMap.put("lo", false);
		}

		@Override
		protected boolean isLogin(HttpServletRequestWrapper request) {
			return request.hasSessionAttribute("account", AdminAccount.class);
		}

		@Override
		protected String getTaskName(HttpServletRequestWrapper request) {
			return request.getParameter("req");
		}
	}

}

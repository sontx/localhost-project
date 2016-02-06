package in.sontx.web.local.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.internal.NotNull;

import in.sontx.web.shared.net.www.HttpServletRequestWrapper;
import in.sontx.web.shared.net.www.HttpServletResponseWrapper;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final String RESULT_OK = "OK";
	protected static final String RESULT_FAIL = "FAIL";

	protected abstract void doWork(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException;

	public static void forward(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
			@NotNull String page) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
	
	public static void sendRedirect(@NotNull HttpServletResponseWrapper response, @NotNull String page) throws IOException {
		response.response.sendRedirect(page);
	}

	protected void forward(@NotNull HttpServletRequestWrapper request, @NotNull HttpServletResponseWrapper response,
			@NotNull String page) throws ServletException, IOException {
		forward(request.request, response.response, page);
	}

	protected void responseResult(HttpServletResponseWrapper response, @NotNull String result) throws IOException {
		response.response.getWriter().write(result);
	}

	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doWork(new HttpServletRequestWrapper(request), new HttpServletResponseWrapper(response));
	}

	protected final void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

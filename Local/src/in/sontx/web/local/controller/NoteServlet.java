package in.sontx.web.local.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.Note;
import in.sontx.web.local.bo.NoteMgr;
import in.sontx.web.local.service.ResourceManager;
import in.sontx.web.shared.net.www.HttpServletRequestWrapper;
import in.sontx.web.shared.net.www.HttpServletResponseWrapper;
import in.sontx.web.shared.net.www.Rule;
import in.sontx.web.shared.net.www.SimpleRule;

@WebServlet("/note")
public class NoteServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private Rule noteRule = new NoteRule();

	private void gotoLogin(HttpServletResponseWrapper response) throws IOException {
		sendRedirect(response, "welcome");
	}

	private void addNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, NoteMgr noteMgr)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		boolean ret = noteMgr.addNote(title, content);
		responseResult(response, ret ? RESULT_OK : RESULT_FAIL);
	}

	private void searchNotes(HttpServletRequestWrapper request, HttpServletResponseWrapper response, NoteMgr noteMgr)
			throws ServletException, IOException {
		String pattern = request.getParameter("s");
		List<Note> notes = noteMgr.searchNotes(pattern);
		request.setAttribute("notes", notes);
		forward(request, response, "note/note.jsp");
	}

	private void editNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, NoteMgr noteMgr)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Note note = noteMgr.getNote(id);
		request.setAttribute("note", note);
		forward(request, response, "note/editor.jsp");
	}

	private void updateNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, NoteMgr noteMgr)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String id = request.getParameter("id");
		boolean ret = noteMgr.updateNote(id, title, content);
		responseResult(response, ret ? RESULT_OK : RESULT_FAIL);
	}

	private void deleteNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, NoteMgr noteMgr)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		boolean ret = noteMgr.deleteNote(id);
		responseResult(response, ret ? RESULT_OK : RESULT_FAIL);
	}

	@Override
	protected void doWork(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		if (noteRule.executable(request)) {
			String req = request.getParameter("req");
			NoteMgr noteMgr = ResourceManager.getInstance().getNoteMgr(request.getSession().getId());
			if ("add".equals(req)) {
				addNote(request, response, noteMgr);
			} else if ("search".equals(req)) {
				searchNotes(request, response, noteMgr);
			} else if ("edit".equals(req)) {
				editNote(request, response, noteMgr);
			} else if ("update".equals(req)) {
				updateNote(request, response, noteMgr);
			} else if ("del".equals(req)) {
				deleteNote(request, response, noteMgr);
			} else {
				searchNotes(request, response, noteMgr);
			}
		} else {
			gotoLogin(response);
		}
	}

	private static class NoteRule extends SimpleRule {

		@Override
		protected boolean isLogin(HttpServletRequestWrapper request) {
			return request.hasSessionAttribute("account", Account.class);
		}

		@Override
		protected String getTaskName(HttpServletRequestWrapper request) {
			return request.getParameter("req");
		}

		@Override
		protected void setupTaskMap(HashMap<String, Boolean> taskMap) {
			taskMap.put("add", false);
			taskMap.put("search", false);
			taskMap.put("edit", false);
			taskMap.put("update", false);
			taskMap.put("del", false);
		}

	}
}

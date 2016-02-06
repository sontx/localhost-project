package in.sontx.web.local.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.Note;
import in.sontx.web.local.bo.UserMgr;
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
	
	private void addNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, UserMgr userMgr)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Note note = new Note();
		note.setTitle(title);
		note.setContent(content);
		boolean ret = userMgr.noteMgr.addNote(note);
		responseResult(response, ret ? RESULT_OK : RESULT_FAIL);
	}
	
	private void searchNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, UserMgr userMgr)
			throws ServletException, IOException {
		String pattern = request.getParameter("s");
		List<Note> notes = userMgr.noteMgr.searchNotes(pattern);
		request.setAttribute("notes", notes);
		forward(request, response, "note/note.jsp");
	}
	
	private void editNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, UserMgr userMgr)
			throws ServletException, IOException {
		int id = request.getInt("id", -1);
		Note note = userMgr.noteMgr.getNote(id);
		request.setAttribute("note", note);
		forward(request, response, "note/editor.jsp");
	}
	
	private void updateNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, UserMgr userMgr)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int id = request.getInt("id", -1);
		Note note = new Note();
		note.setId(id);
		note.setTitle(title);
		note.setContent(content);
		boolean ret = userMgr.noteMgr.updateNote(note);
		responseResult(response, ret ? RESULT_OK : RESULT_FAIL);
	}
	
	private void deleteNote(HttpServletRequestWrapper request, HttpServletResponseWrapper response, UserMgr userMgr)
			throws ServletException, IOException {
		int id = request.getInt("id", -1);
		boolean ret = userMgr.noteMgr.deleteNote(id);
		responseResult(response, ret ? RESULT_OK : RESULT_FAIL);
	}
	
	@Override
	protected void doWork(HttpServletRequestWrapper request, HttpServletResponseWrapper response)
			throws ServletException, IOException {
		if (noteRule.executable(request)) {
			String req = request.getParameter("req");
			UserMgr userMgr = ResourceManager.getInstance().getUserSession(request.getSession().getId());
			if ("add".equals(req)) {
				addNote(request, response, userMgr);
			} else if ("search".equals(req)) {
				searchNote(request, response, userMgr);
			} else if ("edit".equals(req)) {
				editNote(request, response, userMgr);
			} else if ("update".equals(req)) {
				updateNote(request, response, userMgr);
			} else if ("del".equals(req)) {
				deleteNote(request, response, userMgr);
			} else {
				searchNote(request, response, userMgr);
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

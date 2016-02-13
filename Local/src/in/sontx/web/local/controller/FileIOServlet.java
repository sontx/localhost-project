package in.sontx.web.local.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import in.sontx.web.local.Config;
import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.Storage;
import in.sontx.web.local.bo.FileMgr;
import in.sontx.web.local.service.ResourceManager;
import in.sontx.web.shared.net.www.HttpServletRequestWrapper;

@WebServlet("/fileio")
@MultipartConfig
public class FileIOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
				return new String(filename.getBytes(Charset.forName("iso-8859-1")), Charset.forName("UTF-8"));
			}
		}
		return null;
	}
	
	private Storage saveToDb(FileMeta fileMeta, String sessionId) {
		FileMgr fileMgr = ResourceManager.getInstance().getFileMgr(sessionId); 
		return fileMgr.saveFile(fileMeta.getFileName(), fileMeta.getFileSize(), fileMeta.getFileType());
	}
	
	private boolean saveToDisk(Storage storage, InputStream in) {
		FileOutputStream out = null;
		File file = ResourceManager.getInstance().getFileManager().createFile(storage.getRawName());
		try {
			out = new FileOutputStream(file);
			byte[] buff = new byte[Config.FILE_BUFFER];
			int chunk;
			while ((chunk = in.read(buff)) > 0) {
				out.write(buff, 0, chunk);
			}
			out.close();
			return true;
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			if (file.exists())
				file.delete();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

	private void doUpload(FileMeta fileMeta, HttpServletRequestWrapper req) {
		Storage storage = saveToDb(fileMeta, req.getSession().getId());
		saveToDisk(storage, fileMeta.getFileStream());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpServletRequestWrapper _req = new HttpServletRequestWrapper(req);
		if (_req.hasSessionAttribute("account", Account.class)) {
			Part filePart = req.getPart("file");
			if (filePart != null) {
				int fileSize = (int) filePart.getSize();
				String fileName = getFilename(filePart);
				if (fileSize <= Config.FILE_SIZE_MAX_SUPPORTED && (fileName != null && fileName.length() > 0)) {
					String fileType = filePart.getContentType();
					InputStream fileStream = filePart.getInputStream();
					FileMeta fileMeta = new FileMeta();
					fileMeta.setFileName(fileName);
					fileMeta.setFileSize(fileSize);
					fileMeta.setFileType(fileType);
					fileMeta.setFileStream(fileStream);
					doUpload(fileMeta, _req);
					filePart.delete();
				}
			}
		} else {
			resp.sendRedirect("welcome");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	private static class FileMeta {
		private String fileName;
		private String fileType;
		private int fileSize;
		private InputStream fileStream;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public int getFileSize() {
			return fileSize;
		}

		public void setFileSize(int fileSize) {
			this.fileSize = fileSize;
		}

		public InputStream getFileStream() {
			return fileStream;
		}

		public void setFileStream(InputStream fileStream) {
			this.fileStream = fileStream;
		}
	}
}

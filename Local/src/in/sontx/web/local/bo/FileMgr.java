package in.sontx.web.local.bo;

import java.util.List;
import java.util.regex.Pattern;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.Config;
import in.sontx.web.local.bean.Storage;
import in.sontx.web.local.dao.FileDb;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.utils.DateTime;
import in.sontx.web.shared.utils.Security;

public final class FileMgr {
	private final String userId;
	private final FileDb fileDb;
	private final Pattern fileNamePattern = Pattern.compile(Config.FILE_NAME_PATTERN);

	public FileMgr(@NotNull ISQLConnection connection, @NotNull String userId) {
		this.fileDb = new FileDb(connection);
		this.userId = userId;
	}

	private boolean isValidFileName(String fileName) {
		return fileName == null || (fileName.trim().length() == 0) || !fileNamePattern.matcher(fileName).matches();
	}

	public Storage saveFile(String fileName, int size, String type) {
		if (!isValidFileName(fileName) || size < 1)
			return null;
		String id = Security.getRandomUUID();
		String rawName = Security.getRandomUUID();
		int created = DateTime.now().getTime();
		byte state = Storage.SHARED_STATE_NONE;

		Storage storage = new Storage();
		storage.setId(id);
		storage.setOriginName(fileName);
		storage.setRawName(rawName);
		storage.setSize(size);
		storage.setCreated(created);
		storage.setState(state);
		storage.setType(type);

		return fileDb.saveFile(userId, storage) ? storage : null;
	}

	public boolean changeFileName(String id, String newFileName) {
		return isValidFileName(newFileName) && Security.isValidUUID(id) ? 
				fileDb.changeFileName(userId, id, newFileName)
				: false;
	}

	public boolean changeFileState(String id, byte newState) {
		return Security.isValidUUID(id) && newState >= 0 && newState <= 2 ? 
				fileDb.changeFileState(userId, id, newState)
				: false;
	}

	public Storage getFile(String id) {
		return Security.isValidUUID(id) ? fileDb.getFile(userId, id) : null;
	}

	public List<Storage> getAllFilesSortByName() {
		return fileDb.getAllFilesSortByName(userId);
	}

	public List<Storage> getAllFilesSortBySize() {
		return fileDb.getAllFilesSortBySize(userId);
	}

	public List<Storage> getAllFilesSortByCreatedTime() {
		return fileDb.getAllFilesSortByCreatedTime(userId);
	}

	public List<Storage> searchFiles(String what) {
		return what == null || (what = what.trim()).length() == 0 ? null : fileDb.searchFiles(userId, what);
	}

	public boolean deleteFile(String id) {
		return Security.isValidUUID(id) ? fileDb.deleteFile(userId, id) : false;
	}
}

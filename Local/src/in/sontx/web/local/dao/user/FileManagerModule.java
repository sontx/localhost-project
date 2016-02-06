package in.sontx.web.local.dao.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import in.sontx.web.shared.utils.Path;

public class FileManagerModule {
	private final File userDir;
	private final File storeDir;

	private void createIfNecessary(File dir) {
		if (!dir.exists())
			dir.mkdirs();
	}

	private void createIfNecessary() {
		createIfNecessary(userDir);
		createIfNecessary(storeDir);
	}

	public void cleanDir() {
		File[] files = storeDir.listFiles();
		for (File file : files) {
			file.delete();
		}
	}

	private boolean checkName(String name) {
		return Path.isName(name);
	}

	private File getFile(String name, File dir) throws SecurityException {
		if (!checkName(name))
			throw new SecurityException();
		return new File(Path.combine(dir.getPath(), name));
	}

	public void saveFile(InputStream in, String name) throws IOException, SecurityException {
		File file = getFile(name, storeDir);
		if (file.exists())
			file.delete();
		FileOutputStream out = new FileOutputStream(file);
		byte[] buff = new byte[32768];
		int chunk;
		while ((chunk = in.read(buff)) > 0) {
			out.write(buff, 0, chunk);
		}
		out.close();
	}

	public boolean deleteFile(String name) throws SecurityException, FileNotFoundException {
		File file = getFile(name, storeDir);
		if (!file.exists())
			throw new FileNotFoundException(name);
		return file.delete();
	}

	public boolean renameFile(String name, String newName) throws SecurityException, FileNotFoundException {
		File file = getFile(name, storeDir);
		File newFile = getFile(newName, storeDir);
		return file.renameTo(newFile);
	}

	public InputStream readFile(String name) throws SecurityException, FileNotFoundException {
		File file = getFile(name, storeDir);
		return new FileInputStream(file);
	}

	public boolean checkExists(String name) {
		try {
			File file = getFile(name, storeDir);
			return file.exists();
		} catch (SecurityException e) {
			return false;
		}
	}

	public FileManagerModule(String dir, String storeDirName) {
		this.userDir = new File(Path.validPart(dir));
		this.storeDir = new File(Path.combine(dir, storeDirName));
		createIfNecessary();
	}
}

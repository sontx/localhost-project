package in.sontx.web.local.bean;

import com.sun.istack.internal.NotNull;

public class Note {
	private String id;
	private String title;
	private String content;
	private int created;
	private int modified;

	@NotNull
	public String getTitle() {
		return title;
	}

	public void setTitle(@NotNull String title) {
		this.title = title;
	}

	@NotNull
	public String getContent() {
		return content;
	}

	public void setContent(@NotNull String content) {
		this.content = content;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getModified() {
		return modified;
	}

	public void setModified(int modified) {
		this.modified = modified;
	}
}

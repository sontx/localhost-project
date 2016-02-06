package in.sontx.web.local.bean;

import com.sun.istack.internal.NotNull;

public class Note {
	private int id;
	private String title;
	private String content;
	private int created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
}

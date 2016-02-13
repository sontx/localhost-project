package in.sontx.web.local.bean;

public class Storage {
	public static final byte SHARED_STATE_NONE = 0;
	public static final byte SHARED_STATE_LINK = 1;
	public static final byte SHARED_STATE_EVERYONE = 2;
	private String id;
	private String originName;// local name
	private String rawName;// cloud name
	private String type;
	private int size;
	private int created;
	private byte state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getRawName() {
		return rawName;
	}

	public void setRawName(String rawName) {
		this.rawName = rawName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean sharealbe() {
		return state == SHARED_STATE_LINK || state == SHARED_STATE_EVERYONE;
	}
}

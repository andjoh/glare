package dal;

public class PictureData {
	private String id;
	private String urlStd;
	private String urlThumb;
	private long createdTime;
	private String hashtag;
	private boolean removeFlag;
	
	public PictureData() {}
	
	public PictureData(String id, String urlStd, String urlThumb, long createdTime, String hashtag, boolean removeFlag){
		this.id = id;
		this.urlStd = urlStd;
		this.urlThumb = urlThumb;
		this.createdTime = createdTime;
		this.hashtag = hashtag;
		this.removeFlag = removeFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrlStd() {
		return urlStd;
	}

	public void setUrlStd(String urlStd) {
		this.urlStd = urlStd;
	}

	public String getUrlThumb() {
		return urlThumb;
	}

	public void setUrlThumb(String urlThumb) {
		this.urlThumb = urlThumb;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public boolean isRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}
}

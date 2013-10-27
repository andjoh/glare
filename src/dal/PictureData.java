package dal;

public class PictureData {
	private String id;
	private String urlStd;
	private String urlThumb;
	private long time;
	private String hashtag;
	private boolean removeFlag;
	
	public PictureData() {}
	
//	public PictureData(String id, String urlStd, String urlThumb, long time, String hashtag, boolean removeFlag){
//		this.id = id;
//		this.urlStd = urlStd;
//		this.urlThumb = urlThumb;
//		this.time = time;
//		this.removeFlag = removeFlag;
//	}

	public String getId() {
		return id;
	}

	public void setId(String picID) {
		this.id = picID;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
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

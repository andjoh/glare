package dal;

import java.util.*;

/**
 * A class that represents all metadata in a picture
 * 
 * @author Andreas Bjerga & Marius Vasshus
 */

public class PictureData {
	private String id;
	private String urlStd;
	private String urlThumb;
	private long createdTime;
	private boolean removeFlag;

	private Set<Hashtag> hashtags;

	public PictureData() {
		hashtags = new HashSet<Hashtag>();
	}

	public PictureData(String id, String urlStd, String urlThumb, long createdTime, boolean removeFlag){
		this.id = id;
		this.urlStd = urlStd;
		this.urlThumb = urlThumb;
		this.createdTime = createdTime;
		this.removeFlag = removeFlag;
		hashtags = new HashSet<Hashtag>();
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

	public boolean isRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}

	public Set<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	public void addHashtag(Hashtag ht) {
		hashtags.add(ht);
		ht.getPictures().add(this);
	}

	public void remHashtag(Hashtag ht) {
		hashtags.remove(ht);
	}

	public void remAllHashtags() {
		hashtags = new HashSet<Hashtag>();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PictureData) {
			PictureData p = (PictureData) o;
			if (p.id.equals(id))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "PictureData [id=" + id + ", hashtags=" + hashtags + "]";
	}
}

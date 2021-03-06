package dal;

import java.util.HashSet;
import java.util.Set;

/**
 * A class to represent a Hashtag
 * 
 * @author Andreas Bjerga & Marius Vasshus
 */

public class Hashtag {
	private int id;
	private String hashtag;

	private Set<PictureData> pictures;

	public Hashtag() {
		pictures = new HashSet<PictureData>();
	}

	public Hashtag(String hashtag) {
		this.hashtag = hashtag.toLowerCase();
		pictures = new HashSet<PictureData>();
	}

	public Set<PictureData> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureData> pics) {
		this.pictures = pics;
	}

	public void addPicToHashtag(PictureData pd) {
		pictures.add(pd);
		pd.getHashtags().add(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	@Override
	public int hashCode() {
		int tmp = 0;
		tmp = (id + hashtag).hashCode();
		return tmp;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Hashtag) {
			Hashtag h = (Hashtag) o;
			if (h.hashtag.equals(hashtag))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Hashtag [hashtag=" + hashtag + "]";
	}

}
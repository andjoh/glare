package dal;

import java.util.*;

public class PictureIdAndHashtags {
	private String picId;
	private Set<String> hashtags;
	
	public PictureIdAndHashtags(String picId, Set<String> hashtags) {
		this.picId    = picId;
		this.hashtags = hashtags;
	}

	public String getPicId() {
		return picId;
	}

	public Set<String> getHashtags() {
		return hashtags;
	}
}
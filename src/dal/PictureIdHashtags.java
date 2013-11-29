package dal;

import java.util.*;

public class PictureIdHashtags {
	public String picId;
	public Set<String> hashtags;
	
	public PictureIdHashtags(String picId, Set<String> hashtags) {
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
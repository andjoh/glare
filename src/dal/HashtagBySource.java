package dal;

import java.util.*;

public class HashtagBySource {
	private String source;
	private Set<String> hashtag;

	
	public HashtagBySource() {
		hashtag = new HashSet<String>();
	}
	
	
	public String getSource() {
		return source;
	}
	
	
	public void setSource(String source) {
		this.source = source;
	}
	
	
	public Set<String> getHashtag() {
		return hashtag;
	}

	
	public void setHashtag(Set<String> hashtag) {
		this.hashtag.addAll(hashtag);
	}
	
	
	public void setHashtag(String hashtag) {
		this.hashtag.add(hashtag);
	}
	

	public void removeHashtag(String hashtag) {
		this.hashtag.remove(hashtag);
	}
	
	
	public void clearHashtag() {
		this.hashtag.clear();
	}
}

package dal;

import java.util.*;

public class HashtagBySource {
	private String source;
	private List<String> hashtag;

	public HashtagBySource() {
		hashtag = new ArrayList<String>();
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getHashtag() {
		return hashtag;
	}
	public void setHashtag(String ht) {
		// TODO Check/remove duplicates
		hashtag.add(ht);
	}
	
	public void setAllHashtag(List<String> ht) {
		// TODO Check/remove duplicates
		hashtag = ht;
	}
}

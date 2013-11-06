package dal;

import java.util.HashSet;
import java.util.Set;

public class Source {
	private int id;
	private String source;
	
	private Set<Hashtag> hashtags;
	
	public Source(String source){
		this.source = source;
		hashtags = new HashSet<Hashtag>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Set<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}
	
	public void addHashtag(Hashtag h){
		hashtags.add(h);
		h.getSources().add(this);
	}

}

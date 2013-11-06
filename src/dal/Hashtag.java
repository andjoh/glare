package dal;

import java.util.HashSet;
import java.util.Set;

public class Hashtag {
	private int id;
	private String hashtag;
	
	private Set<Source> sources;
	
	public Hashtag(String hashtag){
		this.hashtag = hashtag;
		sources = new HashSet<Source>();
	}
	
	public Set<Source> getSources() {
		return sources;
	}

	public void setSources(Set<Source> sources) {
		this.sources = sources;
	}
	
	public void addGivenSource(Source s){
		sources.add(s);
		s.getHashtags().add(this);
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
		return hashtag.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Hashtag){
			Hashtag h = (Hashtag)o;
			if(h.hashtag.equals(hashtag)) return true;
		}
		return false;
	}
	
}
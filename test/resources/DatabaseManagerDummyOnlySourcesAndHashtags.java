package resources;

import java.util.*;

import dal.*;


public class DatabaseManagerDummyOnlySourcesAndHashtags extends DatabaseManager {
	private List<String> sources;
	private Set<String> hashtags;
	
	public DatabaseManagerDummyOnlySourcesAndHashtags(DatabaseHandler databaseHandler) {
		super(databaseHandler);
		sources  = new ArrayList<String>();
		hashtags = new HashSet<String>();
	}
	
	public List<String> getSources() {	
		return sources;
	}
	
	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	
	public Set<String> getHashtags() {
		return hashtags;
	}
	
	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}	
}

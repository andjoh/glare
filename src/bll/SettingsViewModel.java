package bll;

public class SettingsViewModel {
	private String id;
	private String urlThumb;
	
	
	public SettingsViewModel(String id, String urlThumb){
		this.id = id;
		this.urlThumb = urlThumb;
	}

	
	public String getId() {
		return id;
	}

	
	public String getUrlThumb() {
		return urlThumb;
	}
}

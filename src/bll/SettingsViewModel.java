package bll;

import java.awt.image.BufferedImage;

public class SettingsViewModel {
	private BufferedImage thumbnail;     // Not sure if this is correct type?
	private String id;


	public SettingsViewModel(BufferedImage thumbnail, String id) {
		this.thumbnail = thumbnail;
		this.id        = id;
	}
	
	
	public BufferedImage getThumbnail() {
		return thumbnail;
	}


	public String getId() {
		return id;
	}
}
